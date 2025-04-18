package com.hung.shop.product.repository;

import com.hung.shop.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {

}
