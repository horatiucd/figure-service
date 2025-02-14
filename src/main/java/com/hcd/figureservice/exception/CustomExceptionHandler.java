package com.hcd.figureservice.exception;

import com.hcd.figureservice.controller.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleException(Exception ex, WebRequest request, HttpStatus status) {
        ServletWebRequest servletRequest = (ServletWebRequest) request;
        ErrorResponse error = new ErrorResponse(status.getReasonPhrase(), status.value(),
                ex.getLocalizedMessage(), servletRequest.getRequest().getRequestURI());
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}
