package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "buildWith")
@Entity
@With
@Table(name = "product_slots")
public class ProductSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long productSlotId;

    @Column(name = "product_id")
    private  Long productId;

    @Column(name = "slot_id")
    private  Long slotId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AuctionStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column (name = "updated_at")
    private LocalDateTime updatedAt;
}
