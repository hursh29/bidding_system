package com.intuit.bidding_system.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "winners")
@Data
@Builder(setterPrefix = "buildWith")
public class BidWinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long winnerId;

    @Column(name = "product_slot_id")
    private Long productSlotId;

    @Column(name = "bid_id")
    private Long bidId;

    @Column(name = "selected_at")
    private LocalDateTime selectedAt;
}
