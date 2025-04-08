package com.intuit.bidding_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No active slots are present, bidding not possible")
public class InvalidBidException extends Exception {
    public InvalidBidException (String message) {
        super(message);
    }

    public InvalidBidException () {
        super();
    }
}
