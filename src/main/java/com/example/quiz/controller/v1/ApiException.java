package com.example.quiz.controller.v1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ApiException {

    private HttpStatus status;
    private String message;
    private List<String> exceptions;

    public ApiException(HttpStatus status, String message, List<String> exceptions) {
        this.status = status;
        this.message = message;
        this.exceptions = exceptions;
    }

    public ApiException(HttpStatus status, String message, String exception) {
        this.status = status;
        this.message = message;
        this.exceptions = Collections.singletonList(exception);
    }

    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
