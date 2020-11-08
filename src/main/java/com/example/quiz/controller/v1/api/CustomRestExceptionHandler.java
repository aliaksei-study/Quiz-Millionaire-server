package com.example.quiz.controller.v1.api;

import com.example.quiz.controller.v1.ApiException;
import com.example.quiz.exception.*;
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

    @ExceptionHandler({AnswerNotFoundException.class})
    public ResponseEntity<ApiException> handleAnswerNotFoundException(AnswerNotFoundException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({QuestionNotFoundException.class})
    public ResponseEntity<ApiException> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({QuestionAlreadyLikedException.class})
    public ResponseEntity<ApiException> handleQuestionAlreadyLiked(QuestionAlreadyLikedException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_MODIFIED, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({QuestionAlreadyDislikedException.class})
    public ResponseEntity<ApiException> handleQuestionAlreadyDisliked(QuestionAlreadyDislikedException ex) {
        ApiException apiException = new ApiException(HttpStatus.NOT_MODIFIED, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<ApiException> handleInvalidCredentials(InvalidCredentialsException ex) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getStatus());
    }
}
