package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@Builder(setterPrefix = "buildWith")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Long notificationId;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String message;

    @Column(name = "notification_status")
    private NotificationStatus notificationStatus;

    @Column
    private NotificationType type;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
