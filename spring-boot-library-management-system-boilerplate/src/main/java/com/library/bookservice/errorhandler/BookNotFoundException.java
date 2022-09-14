package com.library.bookservice.errorhandler;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super();
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
