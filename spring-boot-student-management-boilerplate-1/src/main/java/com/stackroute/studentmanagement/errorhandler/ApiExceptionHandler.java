package com.stackroute.studentmanagement.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*
Add appropriate annotation/s to create a bean for Global exception handling
*/
@RestControllerAdvice
public class ApiExceptionHandler {

    /*
        create a handler method for below exceptional condition:
        condition : student not found
        body: failure message - http status: 404
     */



    /*
        create a handler method for below exceptional condition:
        condition : student already exists
        body: failure message - http status: 409
     */

	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public String handleProductNotFound(StudentNotFoundException e) {
//        System.out.println("*** inside handleProductNotFound"+e.getMessage());
        String msg = e.getMessage();
        return msg;
    }
	
	@ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StudentAlreadyExistsException.class)
    public String handleInvalid(StudentAlreadyExistsException e) {
        String msg = e.getMessage();
        return msg;
    }

}
