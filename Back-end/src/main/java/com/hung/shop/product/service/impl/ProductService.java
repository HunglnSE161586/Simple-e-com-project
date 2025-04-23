package com.hung.shop.product.service.impl;

import com.hung.shop.categories.service.ICategoryService;
import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.dto.response.ProductDto;
import com.hung.shop.product.entity.Products;
import com.hung.shop.product.mapper.ProductMapper;
import com.hung.shop.product.repository.ProductRepository;
import com.hung.shop.product.service.IProductService;
import com.hung.shop.share.ProductPojo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
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

    @Override
    @Transactional
    public ProductDto createProduct(ProductCreateRequest productCreateRequest) {
        Products product = productMapper.toEntity(productCreateRequest);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Products product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productUpdateRequest, product)));
    }

    @Override
    @Transactional
    public ProductDto softDeleteProduct(Long id) {
        Products product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setIsActive(false);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto restoreProduct(Long id) {
        Products product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setIsActive(true);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Optional<ProductPojo> findById(Long id) {
        return productRepository.findById(id).map(productMapper::toPojo);
    }
}
