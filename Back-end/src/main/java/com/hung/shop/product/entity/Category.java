package com.hung.shop.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    private String description;
    private LocalDateTime createdAt;
    private String image;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
