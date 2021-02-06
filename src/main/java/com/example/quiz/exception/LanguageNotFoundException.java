package com.example.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LanguageNotFoundException extends Exception {
    public LanguageNotFoundException(String message) {
        super(message);
    }

    public LanguageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
