package com.hung.shop.product.service.impl;

import com.hung.shop.product.repository.ProductRepository;
import com.hung.shop.product.service.IProductExistenceChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductExistenceChecker implements IProductExistenceChecker {
    private final ProductRepository productRepository;
    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
}
