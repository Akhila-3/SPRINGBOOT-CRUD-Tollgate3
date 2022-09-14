package com.library.bookservice.errorhandler;

public class BookExistsException extends Exception{
    public BookExistsException() {
        super();
    }

    public BookExistsException(String message) {
        super(message);
    }
}
