package com.intuit.bidding_system.model.request;

import lombok.Data;

@Data
public class BidClaimRequest {
    private Long productSlotId;
    private Double bidAmount;
    private Long userId;
}
