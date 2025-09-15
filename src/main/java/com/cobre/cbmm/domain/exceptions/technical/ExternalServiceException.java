package com.cobre.cbmm.domain.exceptions.technical;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExternalServiceException extends RuntimeException {

    private final Integer statusCode;
    private final String responseBody;

    public ExternalServiceException(String message, Integer statusCode, String responseBody, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    @Serial
    private static final long serialVersionUID = -1029621574788030638L;
}
