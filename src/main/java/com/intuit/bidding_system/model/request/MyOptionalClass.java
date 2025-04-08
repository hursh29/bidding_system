package com.intuit.bidding_system.model.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyOptionalClass<T> {
    private T yourobject;

    public T getValue() {
        return yourobject;
    }

    public boolean isPresent() {
        return yourobject != null;
    }
}
