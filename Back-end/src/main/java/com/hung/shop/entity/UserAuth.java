package com.hung.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(updatable = false)
    private String provider;
    private String providerId;
    private String password;
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
