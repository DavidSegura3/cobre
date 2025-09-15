package com.cobre.cbmm.infrastructure.entrypoints.exceptions;


import com.cobre.cbmm.domain.exceptions.business.InsufficientBalanceException;
import com.cobre.cbmm.domain.exceptions.business.ResourceNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@ControllerAdvice
@Data
public class HandlerExceptionCbmm {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> globalNotFoundException(ResourceNotFoundException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(NOT_FOUND.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    @ExceptionHandler(value = {InsufficientBalanceException.class})
    public ResponseEntity<ErrorMessage> globalInsufficientBalanceException(InsufficientBalanceException exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(BAD_REQUEST.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> globalRestClientException(Exception exception, WebRequest request) {

        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .route(request.getDescription(false))
                .build();
        log.error(message.toString());
        return new ResponseEntity<>(message, INTERNAL_SERVER_ERROR);
    }
}
