package com.example.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class QuestionAlreadyDislikedException extends Exception {
    public QuestionAlreadyDislikedException(String message) {
        super(message);
    }

    public QuestionAlreadyDislikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
