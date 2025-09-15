package com.cobre.cbmm.infrastructure.drivenadapters.adapters;

import com.cobre.cbmm.domain.models.account.Account;
import com.cobre.cbmm.domain.models.account.gateway.AccountRepositoryPort;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.AccountDataDAO;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.data.AccountData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountDataDAO accountDataDAO;
    @Override
    public Optional<Account> findById(String id) {
        return accountDataDAO.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountData entity = toEntity(account);
        return toDomain(accountDataDAO.save(entity));
    }

    private AccountData toEntity(Account account) {
        return AccountData
                .builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();

    }

    private Account toDomain(AccountData accountData) {
        return Account
                .builder()
                .id(accountData.getId())
                .balance(accountData.getBalance())
                .currency(accountData.getCurrency())
                .build();
    }
}
