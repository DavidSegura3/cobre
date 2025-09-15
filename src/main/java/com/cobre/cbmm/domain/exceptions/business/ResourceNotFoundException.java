package com.cobre.cbmm.domain.exceptions.business;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Doesn't exist record in %s with id: %s";

    public ResourceNotFoundException(String message, String value) {
        super(String.format(ERROR_MESSAGE, message, value));
    }

    @Serial
    private static final long serialVersionUID = -1888020577146662757L;
}
