package com.hung.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false,unique = true, updatable = false)
    private String email;

    private String firstName;

    private String lastName;
    @Column(nullable = false)
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRoles role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAuth> userAuths;

    @PrePersist
    public void prePersist() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
