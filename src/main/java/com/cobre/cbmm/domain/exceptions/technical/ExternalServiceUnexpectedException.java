package com.cobre.cbmm.domain.exceptions.technical;

import java.io.Serial;

public class ExternalServiceUnexpectedException extends RuntimeException {

    public ExternalServiceUnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
    @Serial
    private static final long serialVersionUID = -4558610721041460432L;
}
