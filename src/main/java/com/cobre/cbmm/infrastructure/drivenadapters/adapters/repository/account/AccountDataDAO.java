package com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account;

import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.data.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDataDAO extends JpaRepository<AccountData, String> {
}
