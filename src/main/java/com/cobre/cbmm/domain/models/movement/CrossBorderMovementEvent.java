package com.cobre.cbmm.domain.models.movement;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CrossBorderMovementEvent {

    private String eventId;
    private String eventType;
    private LocalDateTime operationDate;
    private AccountDetails origin;
    private AccountDetails destination;

    @Data
    public static class AccountDetails {
        private String accountId;
        private String currency;
        private BigDecimal amount;
    }
}
