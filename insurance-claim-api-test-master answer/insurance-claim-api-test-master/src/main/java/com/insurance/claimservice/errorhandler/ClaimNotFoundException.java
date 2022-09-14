package com.insurance.claimservice.errorhandler;

public class ClaimNotFoundException extends Exception {
    public ClaimNotFoundException() {
        super();
    }

    public ClaimNotFoundException(String message) {
        super(message);
    }
}
