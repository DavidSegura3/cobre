package com.cobre.cbmm.domain.models.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class Transaction {

    private String id;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String type; // CREDIT o DEBIT
    private LocalDateTime timestamp;
    private String eventId;
}
