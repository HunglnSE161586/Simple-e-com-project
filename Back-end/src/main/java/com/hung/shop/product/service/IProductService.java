package com.hung.shop.product.service;

import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.dto.response.ProductDetailResponse;
import com.hung.shop.product.dto.response.ProductDto;
import com.hung.shop.share.ProductPojo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/* Add javadoc comments for interfaces, public methods/classes.
What are the distinct use cases of DTOs and POJOs? Are POJOs used for inter-service communication only? */
public interface IProductService {
    List<ProductPojo> getAllProducts();
    ProductDetailResponse getProductById(Long id);
    Page<ProductPojo> getPagedProduct(int page, int size);
    ProductDto createProduct(ProductCreateRequest productCreateRequest);
    ProductDto updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
    ProductDto softDeleteProduct(Long id);
    ProductDto restoreProduct(Long id);
    Optional<ProductPojo> findById(Long id);
    Page<ProductPojo> getPagedProductByCategoryId(Long categoryId, int page, int size);
    Page<ProductPojo> getPagedProductByIsFeaturedTrue(int page, int size);
}