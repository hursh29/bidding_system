package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "slots")
@Builder(setterPrefix = "buildWith")
public class BiddingSlot {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long slotId;

        @Column(name = "start_time")
        private LocalDateTime startTime;

        @Column(name = "end_time")
        private LocalDateTime endTime;

        @Enumerated(EnumType.STRING)
        @Column(length = 20, nullable = false)
        private SlotStatus status;

        @Column(name="created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(name="updated_at", nullable = false)
        private LocalDateTime updatedAt;
}
