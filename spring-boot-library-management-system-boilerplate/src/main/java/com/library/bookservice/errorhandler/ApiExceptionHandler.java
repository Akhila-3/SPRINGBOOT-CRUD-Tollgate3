package com.library.bookservice.errorhandler;

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
        condition : book not found
        body: failure message - http status: 404
     */



    /*
        create a handler method for below exceptional condition:
        condition : book already exists
        body: failure message - http status: 409
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> BookNotFoundHandler(BookNotFoundException exception){
        return new ResponseEntity<>("Book Not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookExistsException.class)
    public ResponseEntity<String> bookExistsHandler(BookExistsException exception){
        return new ResponseEntity<>("Book already exists", HttpStatus.CONFLICT);
    }

}