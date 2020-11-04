package com.example.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlayerWithSuchEmailAlreadyExists extends Exception {
    public PlayerWithSuchEmailAlreadyExists(String message) {
        super(message);
    }

    public PlayerWithSuchEmailAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
