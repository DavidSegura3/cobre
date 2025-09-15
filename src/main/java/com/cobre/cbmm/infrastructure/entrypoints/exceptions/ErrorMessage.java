package com.cobre.cbmm.infrastructure.entrypoints.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ErrorMessage {

    private Integer statusCode;
    private String message;
    private String route;
}
