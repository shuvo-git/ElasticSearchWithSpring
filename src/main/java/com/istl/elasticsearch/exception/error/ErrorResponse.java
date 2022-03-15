package com.istl.elasticsearch.exception.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorResponse
{
    private String message;
    private HttpStatus httpStatus;
}
