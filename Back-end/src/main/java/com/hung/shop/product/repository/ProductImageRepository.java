package com.hung.shop.product.repository;

import com.hung.shop.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("""
    SELECT pi FROM ProductImage pi
    WHERE pi.product.id IN :productIds
      AND pi.displayOrder = (
          SELECT MIN(pii.displayOrder)
          FROM ProductImage pii
          WHERE pii.product.id = pi.product.id
      )
    """)
    List<ProductImage> findMainImagesForProductIds(@Param("productIds") List<Long> productIds);

}
