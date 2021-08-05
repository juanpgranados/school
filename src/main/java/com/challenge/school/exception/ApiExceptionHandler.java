package com.challenge.school.exception;

import com.challenge.school.model.ApiExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ApiException.class })
    public ResponseEntity<Object> handleGeneralException(ApiException apiEx) {

        return new ResponseEntity<>(new ApiExceptionResponse(apiEx.getStatus(), apiEx.getMessage()), apiEx.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(new ApiExceptionResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request"), HttpStatus.BAD_REQUEST);
    }

}
