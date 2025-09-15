package com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.account.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "accounts")
@Builder(toBuilder = true)
public class AccountData {

    @Id
    private String id;
    private BigDecimal balance;
    private String currency;
}

