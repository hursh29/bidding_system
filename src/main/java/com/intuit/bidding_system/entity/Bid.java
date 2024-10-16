package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bids")
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

