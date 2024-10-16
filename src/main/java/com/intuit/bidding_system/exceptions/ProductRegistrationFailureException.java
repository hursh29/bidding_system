package com.intuit.bidding_system.exceptions;

public class ProductRegistrationFailureException extends Exception {
    public ProductRegistrationFailureException(String errorMessage) {
        super(errorMessage);
    }
}
