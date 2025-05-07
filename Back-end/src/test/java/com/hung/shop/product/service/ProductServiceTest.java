package com.hung.shop.product.service;

import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.entity.Category;
import com.hung.shop.product.entity.Product;
import com.hung.shop.product.exception.product.ProductNotFoundException;
import com.hung.shop.product.mapper.ProductMapper;
import com.hung.shop.product.repository.ProductRepository;
import com.hung.shop.product.service.impl.ProductService;
import com.hung.shop.productReview.service.IProductReviewService;
import com.hung.shop.share.CategoryPOJO;
import com.hung.shop.share.ProductImagePOJO;
import com.hung.shop.share.ProductReviewPOJO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private IProductImageQueryPort productImageService;

    @Mock
    private IProductMainImageService productMainImageService;

    @Mock
    private ICategoryService categoryService;

    @Mock
    private IProductReviewService productReviewService;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductById_shouldReturnProductDetailResponse_whenProductExists() {
        // Arrange
        Long productId = 1L;
        Category category = new Category();
        category.setId(10L);

        Product product = new Product();
        product.setId(productId);
        product.setCategory(category);

        CategoryPOJO categoryPOJO = new CategoryPOJO();
        List<ProductImagePOJO> productImagePOJOS = List.of(new ProductImagePOJO());
        List<ProductReviewPOJO> productReviewPOJOS = List.of(new ProductReviewPOJO());

        ProductDetailResponse expectedResponse = new ProductDetailResponse();
        expectedResponse.setCategoryPOJO(categoryPOJO);
        expectedResponse.setProductImagePOJOS(productImagePOJOS);
        expectedResponse.setProductReviewPOJOS(productReviewPOJOS);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(categoryService.getCategoryPojoById(category.getId())).thenReturn(categoryPOJO);
        when(productImageService.getProductImagesPojoByProductId(productId)).thenReturn(productImagePOJOS);
        when(productReviewService.getProductReviewsPojoByProductId(productId)).thenReturn(productReviewPOJOS);
        when(productMapper.toDetailResponse(product)).thenReturn(expectedResponse);

        // Act
        ProductDetailResponse actual = productService.getProductById(productId);

        // Assert
        assertNotNull(actual);
        assertEquals(expectedResponse, actual);
        verify(productRepository).findById(productId);
        verify(categoryService).getCategoryPojoById(category.getId());
        verify(productImageService).getProductImagesPojoByProductId(productId);
        verify(productReviewService).getProductReviewsPojoByProductId(productId);
        verify(productMapper).toDetailResponse(product);
    }

    @Test
    void getProductById_shouldThrowException_whenProductNotFound() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository).findById(productId);
    }

    @Test
    void updateProduct_shouldThrowException_whenProductNotFound(){
        // Arrange
        Long productId = 99L;
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, productUpdateRequest));
        verify(productRepository).findById(productId);
    }



}