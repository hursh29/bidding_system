package com.intuit.bidding_system.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Builder(setterPrefix = "buildWith")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    @Column
    private String address;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}