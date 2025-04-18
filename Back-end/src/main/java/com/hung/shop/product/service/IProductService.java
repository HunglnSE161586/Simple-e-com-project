package com.hung.shop.product.service;

import com.hung.shop.product.dto.response.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    public List<ProductDto> getAllProducts();
    public ProductDto getProductById(Long id);
    public Page<ProductDto> getPagedProduct(int page, int size);
}
