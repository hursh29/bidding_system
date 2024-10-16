package com.intuit.bidding_system.exceptions;

public class InvalidBidException extends Exception {
    public InvalidBidException (String message) {
        super(message);
    }

    public InvalidBidException () {
        super();
    }
}
