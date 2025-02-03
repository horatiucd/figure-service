package com.hcd.figureservice.exception;

import com.hcd.figureservice.controller.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(status.value(), ex.getCause().getLocalizedMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(status.value(), ex.getLocalizedMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
    }
}
