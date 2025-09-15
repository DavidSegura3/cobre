package com.cobre.cbmm.domain.exceptions.business;

import java.io.Serial;
import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Insufficient balance in %s balance: %s";

    public InsufficientBalanceException(String message, BigDecimal value) {
        super(String.format(ERROR_MESSAGE, message, value));
    }

    @Serial
    private static final long serialVersionUID = 636674325335085608L;
}
