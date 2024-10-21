package com.example.SecurityApp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<PersonErrorResponse> handleUserNotFoundException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<PersonErrorResponse> handleInvalidLoginException(InvalidLoginException e) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<PersonErrorResponse> handleUserAlreadyExistsException(PersonAlreadyExistsException e) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
