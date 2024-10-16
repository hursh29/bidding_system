package com.intuit.bidding_system.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidSummaryResponse {

    private Long userId;

    private Long productId;

    private String userName;

    private String productName;

    private String productDescription;

    private Double biddingPrice;
}
