package com.example.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PrincipalWithSuchEmailAlreadyExistsException extends Exception {
    public PrincipalWithSuchEmailAlreadyExistsException(String message) {
        super(message);
    }

    public PrincipalWithSuchEmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
