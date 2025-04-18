package com.hung.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    private String description;
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "categories")
    private Set<Products> products = new HashSet<>();


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
