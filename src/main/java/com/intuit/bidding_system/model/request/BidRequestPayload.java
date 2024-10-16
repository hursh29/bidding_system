package com.intuit.bidding_system.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "buildWith")
public class BidRequestPayload {
    private Double bidPrice;
    private Long productSlotId;
}
