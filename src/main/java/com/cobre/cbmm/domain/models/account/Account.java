package com.cobre.cbmm.domain.models.account;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class Account {

    private String id;
    private BigDecimal balance;
    private String currency;
}
