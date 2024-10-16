package com.intuit.bidding_system.model.request;

import com.intuit.bidding_system.entity.Category;
import lombok.Data;

@Data
public class ProductPayload {
    private Long vendorId;

    private String name;

    private String description;

    private Category category;

    private Double basePrice;

    private Boolean isActive;

    private Long slotId;
}
