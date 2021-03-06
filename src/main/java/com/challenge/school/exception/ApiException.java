package com.challenge.school.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Setter
@Getter
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final String message;
}
