package com.hung.shop.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product { /* FIXME Should be singular. Rename Products to Product to follow the convention. The table name 'products' í okay
                        Apply to other entities as well*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price; /* Use BigDecimal for monetary values */

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean isFeatured;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stock;

    private Long categoryId;
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
        this.isFeatured = false;
    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}