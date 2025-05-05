package com.hung.shop.product.repository;

import com.hung.shop.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findAllByIsFeaturedTrue(Pageable pageable);
}
