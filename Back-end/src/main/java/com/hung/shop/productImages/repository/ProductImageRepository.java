package com.hung.shop.productImages.repository;

import com.hung.shop.productImages.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {

}
