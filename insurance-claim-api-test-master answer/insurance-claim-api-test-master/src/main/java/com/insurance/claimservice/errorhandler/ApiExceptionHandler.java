package com.insurance.claimservice.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
Add appropriate annotation/s to create a bean for Global exception handling
*/
@RestControllerAdvice
public class ApiExceptionHandler {

    /*
        create a handler method for below exceptional condition:
        condition : claim not found
        body: failure message - http status: 404
     */

    @ExceptionHandler(ClaimNotFoundException.class)
    public ResponseEntity<String> claimNotFoundHandler(ClaimNotFoundException exception){
        return new ResponseEntity<>("Claim Not found", HttpStatus.NOT_FOUND);
    }

    /*
        create a handler method for below exceptional condition:
        condition : claim already exists
        body: failure message - http status: 409
     */

    @ExceptionHandler(ClaimExistsException.class)
    public ResponseEntity<String> claimExistsHandler(ClaimExistsException exception){
        return new ResponseEntity<>("Claim already exists", HttpStatus.CONFLICT);
    }
}