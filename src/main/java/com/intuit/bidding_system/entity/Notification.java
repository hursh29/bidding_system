package com.intuit.bidding_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "notification")
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
