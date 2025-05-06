package com.hung.shop.product.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    @Column(nullable = false)
    private String imageUrl;

    private String altText;

    private Integer displayOrder;
    private LocalDateTime createdAt;

    private Long productId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

