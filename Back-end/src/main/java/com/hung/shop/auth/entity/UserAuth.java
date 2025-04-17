package com.hung.shop.auth.entity;

import com.hung.shop.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth")
@Data
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(updatable = false)
    private String provider;
    private String providerId;
    private String password;
    @Column(updatable = false)
    private Long userId;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
