package com.hung.shop.product.repository;

import com.hung.shop.product.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Page<Products> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Products> findAllByIsFeaturedTrue(Pageable pageable);
}
