package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "winners")
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
