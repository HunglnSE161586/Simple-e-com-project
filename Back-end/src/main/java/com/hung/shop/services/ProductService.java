package com.hung.shop.services;

import com.hung.shop.dto.respond.ProductDto;
import com.hung.shop.entity.Products;
import com.hung.shop.mapper.ProductMapper;
import com.hung.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }
    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productMapper.toDto(product);
    }
    public Page<ProductDto> getPagedProduct(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }
}
