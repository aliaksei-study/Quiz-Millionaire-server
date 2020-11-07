package com.example.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class QuestionAlreadyLikedException extends Exception {
    public QuestionAlreadyLikedException(String message) {
        super(message);
    }

    public QuestionAlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
