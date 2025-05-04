package com.hung.shop.product.entity;

import com.hung.shop.productImages.entity.ProductImages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products { /* FIXME Should be singular. Rename Products to Product to follow the convention. The table name 'products' í okay
                        Apply to other entities as well*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId; /* Should be renamed to 'id' to follow the best practices. Apply to other entities as well. */

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