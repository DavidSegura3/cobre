package com.cobre.cbmm.domain.exceptions.business;

import java.io.Serial;

public class CurrencyMismatchException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Currency mismatch for %s account";


    public CurrencyMismatchException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }

    @Serial
    private static final long serialVersionUID = 1841640338396617008L;
}
