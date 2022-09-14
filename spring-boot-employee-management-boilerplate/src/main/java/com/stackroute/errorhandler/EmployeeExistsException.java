package com.stackroute.errorhandler;

public class EmployeeExistsException extends Exception{
    public EmployeeExistsException() {
        super();
    }

    public EmployeeExistsException(String message) {
        super(message);
    }
}
