package com.insurance.claimservice.errorhandler;

public class ClaimExistsException extends Exception{
    public ClaimExistsException() {
        super();
    }

    public ClaimExistsException(String message) {
        super(message);
    }
}
