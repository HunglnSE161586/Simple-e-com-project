package com.hung.shop.product.service;

import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.dto.response.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    Page<ProductDto> getPagedProduct(int page, int size);
    ProductDto createProduct(ProductCreateRequest productCreateRequest);
    ProductDto updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
    ProductDto softDeleteProduct(Long id);
    ProductDto restoreProduct(Long id);
    Optional<ProductDto> findById(Long id);
}
