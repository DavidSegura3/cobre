package com.cobre.cbmm.domain.models.account.gateway;

import com.cobre.cbmm.domain.models.account.Account;

import java.util.Optional;

public interface AccountRepositoryPort {

    Optional<Account> findById(String id);
    Account save(Account account);
}
