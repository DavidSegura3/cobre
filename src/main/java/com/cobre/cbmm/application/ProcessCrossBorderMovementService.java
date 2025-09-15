package com.cobre.cbmm.application;

import com.cobre.cbmm.domain.exceptions.business.CurrencyMismatchException;
import com.cobre.cbmm.domain.exceptions.business.InsufficientBalanceException;
import com.cobre.cbmm.domain.exceptions.business.ResourceNotFoundException;
import com.cobre.cbmm.domain.models.account.Account;
import com.cobre.cbmm.domain.models.account.gateway.AccountRepositoryPort;
import com.cobre.cbmm.domain.models.idempotency.gateway.IdempotencyPort;
import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;
import com.cobre.cbmm.domain.usecases.ProcessCrossBorderMovementUseCase;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessCrossBorderMovementService implements ProcessCrossBorderMovementUseCase {

    private final AccountRepositoryPort accountRepository;
    private final IdempotencyPort idempotencyPort;

    @Override
    @Transactional
    @CircuitBreaker(name = "circuitBreakerEvent", fallbackMethod = "reProcessEvent")
    public void process(CrossBorderMovementEvent event) {

        if(idempotencyPort.isProcessed(event.getEventId())){
            return;
        }

        Account originAccount = accountRepository.findById(event.getOrigin().getAccountId())
                .orElseThrow(() -> {
                    log.error("Doesn't exist record with id: " + event.getOrigin().getAccountId());
                    return new ResourceNotFoundException("account", event.getOrigin().getAccountId());
                });

        if (!originAccount.getCurrency().equals(event.getOrigin().getCurrency())) {
            log.error("Currency mismatch for origin account");
            throw new CurrencyMismatchException("origin");
        }

        if (originAccount.getBalance().compareTo(event.getOrigin().getAmount()) < 0) {
            log.error("Insufficient balance");
            throw new InsufficientBalanceException("event", event.getOrigin().getAmount());
        }

        originAccount.setBalance(originAccount.getBalance().subtract(event.getOrigin().getAmount()));
        accountRepository.save(originAccount);
        log.info("save balance in origin account");


        Account destinationAccount = accountRepository.findById(event.getDestination().getAccountId())
                .orElseThrow(() -> {
                    log.error("Doesn't exist record with id: " + event.getDestination().getAccountId());
                    return new ResourceNotFoundException("account", event.getDestination().getAccountId());
                });

        if (!destinationAccount.getCurrency().equals(event.getDestination().getCurrency())) {
            log.error("Currency mismatch for destination account");
            throw new CurrencyMismatchException("destination");
        }

        destinationAccount.setBalance(destinationAccount.getBalance().add(event.getDestination().getAmount()));
        accountRepository.save(destinationAccount);
        log.info("save balance in destination account");
        idempotencyPort.save(event.getEventId());
        log.info("save event in idempotency");
    }

    public void reProcessEvent(CrossBorderMovementEvent event, Exception e) {
        log.error("Circuit breaker is open or call failed. Handling fallback for event: {}", event.getEventId(), e);
    }
}
