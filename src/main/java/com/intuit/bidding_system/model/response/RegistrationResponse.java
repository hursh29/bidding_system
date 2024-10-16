package com.intuit.bidding_system.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "buildWith")
public class RegistrationResponse {
    private Boolean acknowledged;
    private String message;
}
