package com.intuit.bidding_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Data
@Builder(setterPrefix = "buildWith")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;


    @Column(name = "product_slot_id")
    private Long productSlotId;

    @Column(name = "bidder_id")
    private Long bidderId;

    @Column(name = "bid_amount", precision = 10)
    @Positive
    private Double bidAmount;

    @Column(name="bid_time")
    private LocalDateTime bidTime;
}

