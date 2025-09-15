package com.cobre.cbmm.domain.exceptions.technical;

public class RestClientException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Technical error rest client in %s";

    public RestClientException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}
