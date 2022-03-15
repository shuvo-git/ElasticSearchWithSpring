package com.istl.elasticsearch.exception;

import com.istl.elasticsearch.exception.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(value= Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleIllegalArgumentException(Exception e) {
//        return ErrorResponse.builder()
//                .message("argument \"content\" is null")
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//                .build();
//    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponse handleConflict(RuntimeException ex, WebRequest request) {
        return ErrorResponse.builder()
                .message("argument \"content\" is null")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
