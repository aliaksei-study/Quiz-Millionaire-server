package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.ApiException;
import com.example.quiz.exception.PlayerWithSuchEmailAlreadyExists;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PlayerWithSuchEmailAlreadyExists.class})
    public ResponseEntity<ApiException> handlePlayerWithSuchEmailAlreadyExists(PlayerWithSuchEmailAlreadyExists ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

}
