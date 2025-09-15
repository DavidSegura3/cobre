package com.cobre.cbmm.application;

import com.cobre.cbmm.domain.exceptions.business.InsufficientBalanceException;
import com.cobre.cbmm.domain.models.account.Account;
import com.cobre.cbmm.domain.models.account.gateway.AccountRepositoryPort;
import com.cobre.cbmm.domain.models.idempotency.gateway.IdempotencyPort;
import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessCrossBorderMovementServiceTest {

    @Mock
    private AccountRepositoryPort accountRepository;

    @InjectMocks
    private ProcessCrossBorderMovementService processCrossBorderMovementService;

    @Mock
    private IdempotencyPort idempotencyPort;

    private Account originAccount;
    private Account destinationAccount;
    private CrossBorderMovementEvent crossBorderMovementEvent;

    @BeforeEach
    void setUp() {

        originAccount = Account
                .builder()
                .id("ACC123456789")
                .balance(new BigDecimal("200000.00"))
                .currency("COP")
                .build();

        destinationAccount = Account
                .builder()
                .id("ACC987654321")
                .balance(new BigDecimal("0.00"))
                .currency("USD")
                .build();


        crossBorderMovementEvent = new CrossBorderMovementEvent();
        CrossBorderMovementEvent.AccountDetails originDetails = new CrossBorderMovementEvent.AccountDetails();
        originDetails.setAccountId("ACC123456789");
        originDetails.setCurrency("COP");
        originDetails.setAmount(new BigDecimal("15000.50"));

        CrossBorderMovementEvent.AccountDetails destinationDetails = new CrossBorderMovementEvent.AccountDetails();
        destinationDetails.setAccountId("ACC987654321");
        destinationDetails.setCurrency("USD");
        destinationDetails.setAmount(new BigDecimal("880.25"));

        crossBorderMovementEvent.setOrigin(originDetails);
        crossBorderMovementEvent.setDestination(destinationDetails);
    }

    @Test
    void shouldProcessCrossBorderMovementEventSuccessfully() {
        // Arrange
        when(accountRepository.findById("ACC123456789")).thenReturn(Optional.of(originAccount));
        when(accountRepository.findById("ACC987654321")).thenReturn(Optional.of(destinationAccount));

        // Act
        processCrossBorderMovementService.process(crossBorderMovementEvent);

        // Assert
        assertEquals(new BigDecimal("184999.50"), originAccount.getBalance());
        assertEquals(new BigDecimal("880.25"), destinationAccount.getBalance());

        verify(accountRepository, times(1)).save(originAccount);
        verify(accountRepository, times(1)).save(destinationAccount);
    }

    @Test
    void shouldThrowExceptionForInsufficientBalance() {
        // Arrange
        originAccount.setBalance(new BigDecimal("10000.00"));
        when(accountRepository.findById("ACC123456789")).thenReturn(Optional.of(originAccount));

        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> processCrossBorderMovementService.process(crossBorderMovementEvent));
        verify(accountRepository, never()).save(any(Account.class));
    }
}