package com.stackroute.errorhandler;

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
        condition : employee not found
        body: failure message - http status: 404
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> employeeNotFoundHandler(EmployeeNotFoundException exception) {
        return new ResponseEntity<>("Employee Not found", HttpStatus.NOT_FOUND);
    }

    /*
        create a handler method for below exceptional condition:
        condition : employee already exists
        body: failure message - http status: 409
     */
    @ExceptionHandler(EmployeeExistsException.class)
    public ResponseEntity<String> employeeExistsHandler(EmployeeExistsException exception){
        return new ResponseEntity<>("Employee already exists", HttpStatus.CONFLICT);
    }


}
