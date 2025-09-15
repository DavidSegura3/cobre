package com.cobre.cbmm.domain.exceptions.technical;

import java.io.Serial;

public class ExternalServiceConnectivityException extends RuntimeException {

    public ExternalServiceConnectivityException(String message, Throwable cause) {
        super(message, cause);
    }
    @Serial
    private static final long serialVersionUID = -7664780642549150377L;
}
