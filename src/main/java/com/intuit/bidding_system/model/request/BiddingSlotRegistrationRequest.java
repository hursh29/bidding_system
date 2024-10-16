package com.intuit.bidding_system.model.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "buildWith")
public class BiddingSlotRegistrationRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
