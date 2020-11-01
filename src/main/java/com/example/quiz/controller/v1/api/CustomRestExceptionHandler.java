package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.ApiException;
import com.example.quiz.exception.PrincipalWithSuchEmailAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PrincipalWithSuchEmailAlreadyExistsException.class})
    public ResponseEntity<ApiException> handlePrincipalWithSuchEmailAlreadyExists(PrincipalWithSuchEmailAlreadyExistsException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

}
