package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.product.request.ProductCreateRequest;
import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.entity.Category;
import com.hung.shop.product.entity.Product;
import com.hung.shop.product.entity.ProductImage;
import com.hung.shop.share.ProductPojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void shouldMapEntityToDto() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");
        product.setDescription("Description");
        product.setPrice(BigDecimal.valueOf(100));
        product.setIsActive(true);
        product.setIsFeatured(false);
        product.setStock(10L);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Category category = new Category();
        category.setId(5L);
        product.setCategory(category);

        ProductDto dto = productMapper.toDto(product);

        assertEquals(1L, dto.getId());
        assertEquals("Test Product", dto.getProductName());
        assertEquals(5L, dto.getCategoryId());
        assertEquals("Description", dto.getDescription());
        assertEquals(BigDecimal.valueOf(100), dto.getPrice());
        assertTrue(dto.getIsActive());
        assertFalse(dto.getIsFeatured());
        assertEquals(10L, dto.getStock());
        assertNotNull(dto.getCreatedAt());
        assertNotNull(dto.getUpdatedAt());

    }

    @Test
    void shouldMapCreateRequestToEntity() {
        ProductImageCreateRequest imageRequest1 = new ProductImageCreateRequest();
        imageRequest1.setImageUrl("http://example.com/image.jpg");
        imageRequest1.setAltText("Example Image");
        imageRequest1.setDisplayOrder(1);
        ProductImageCreateRequest imageRequest2 = new ProductImageCreateRequest();
        imageRequest2.setImageUrl("http://example.com/image2.jpg");
        imageRequest2.setAltText("Example Image 2");
        imageRequest2.setDisplayOrder(2);

        ProductCreateRequest request = new ProductCreateRequest();
        request.setProductName("New Product");
        request.setDescription("New Desc");
        request.setPrice(BigDecimal.valueOf(200));
        request.setStock(20L);
        request.setCategoryId(7L);
        request.setProductImages(List.of(imageRequest1, imageRequest2));

        Product entity = productMapper.toEntity(request);

        assertEquals("New Product", entity.getProductName());
        assertEquals("New Desc", entity.getDescription());
        assertEquals(BigDecimal.valueOf(200), entity.getPrice());
        assertEquals(20L, entity.getStock());
        assertEquals(7L, entity.getCategory().getId());
        assertEquals(2, entity.getProductImages().size());
        assertEquals("http://example.com/image.jpg", entity.getProductImages().get(0).getImageUrl());
        assertEquals("Example Image", entity.getProductImages().get(0).getAltText());
        assertEquals(1, entity.getProductImages().get(0).getDisplayOrder());
        assertEquals("http://example.com/image2.jpg", entity.getProductImages().get(1).getImageUrl());
        assertEquals("Example Image 2", entity.getProductImages().get(1).getAltText());
        assertEquals(2, entity.getProductImages().get(1).getDisplayOrder());
    }

    @Test
    void shouldUpdateEntityFromUpdateRequest() {
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductName("Updated Name");
        request.setDescription("Updated Desc");
        request.setPrice(BigDecimal.valueOf(300));
        request.setStock(30L);
        request.setCategoryId(8L);
        request.setIsFeatured(true);

        Product existing = new Product();
        existing.setProductName("Old Name");
        existing.setDescription("Old Desc");
        existing.setPrice(BigDecimal.valueOf(100));
        existing.setStock(10L);
        existing.setCategory(new Category());
        existing.setIsFeatured(false);

        productMapper.toEntity(request, existing);

        assertEquals("Updated Name", existing.getProductName());
        assertEquals("Updated Desc", existing.getDescription());
        assertEquals(BigDecimal.valueOf(300), existing.getPrice());
        assertEquals(30L, existing.getStock());
        assertEquals(8L, existing.getCategory().getId());
        assertTrue(existing.getIsFeatured());
    }

    @Test
    void shouldIgnoreNullValuesWhenUpdatingEntity() {
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductName(null);
        request.setCategoryId(null);


        Product existing = new Product();
        existing.setProductName("Old Name");
        Category existingCategory = new Category();
        existingCategory.setId(5L);
        existing.setCategory(existingCategory);
        existing.setDescription("Old Desc");
        existing.setPrice(BigDecimal.valueOf(100));
        existing.setStock(10L);


        productMapper.toEntity(request, existing);

        // Assert that existing values are preserved
        assertEquals("Old Name", existing.getProductName());
        assertEquals(5L, existing.getCategory().getId());
        assertEquals("Old Desc", existing.getDescription());
        assertEquals(BigDecimal.valueOf(100), existing.getPrice());
        assertEquals(10L, existing.getStock());
    }


    @Test
    void shouldMapEntityToPojo() {
        Product product = new Product();
        product.setId(2L);
        Category category = new Category();
        category.setId(9L);
        product.setCategory(category);
        product.setProductName("Test Product");
        product.setDescription("Description");
        product.setPrice(BigDecimal.valueOf(100));
        product.setIsActive(true);
        product.setIsFeatured(false);
        product.setStock(10L);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        ProductPojo pojo = productMapper.toPojo(product);

        assertEquals(2L, pojo.getId());
        assertEquals(9L, pojo.getCategoryId());
        assertEquals("Test Product", pojo.getProductName());
        assertEquals("Description", pojo.getDescription());
        assertEquals(BigDecimal.valueOf(100), pojo.getPrice());
        assertTrue(pojo.getIsActive());
        assertFalse(pojo.getIsFeatured());
        assertEquals(10L, pojo.getStock());
        assertNotNull(pojo.getCreatedAt());
        assertNotNull(pojo.getUpdatedAt());
    }

    @Test
    void shouldMapToDetailResponse() {
        Product product = new Product();
        product.setId(3L);
        product.setProductName("Test Product");
        product.setDescription("Description");
        product.setPrice(BigDecimal.valueOf(150));
        product.setIsActive(true);
        product.setIsFeatured(false);
        product.setStock(20L);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Category category = new Category();
        category.setId(6L);
        product.setCategory(category);

        ProductDetailResponse response = productMapper.toDetailResponse(product);

        assertEquals(3L, response.getId());
        assertEquals(6L, response.getCategoryPOJO().getId());
        assertEquals("Test Product", response.getProductName());
        assertEquals("Description", response.getDescription());
        assertEquals(BigDecimal.valueOf(150), response.getPrice());
        assertTrue(response.getIsActive());
        assertFalse(response.getIsFeatured());
        assertEquals(20L, response.getStock());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());

    }
}
