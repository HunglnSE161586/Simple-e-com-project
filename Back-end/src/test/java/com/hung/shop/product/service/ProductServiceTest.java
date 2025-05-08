package com.hung.shop.product.service;

import com.hung.shop.product.dto.product.request.ProductCreateRequest;
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
import com.hung.shop.share.ProductPojo;
import com.hung.shop.share.ProductReviewPOJO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
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
    void createProduct_shouldSaveAndReturnProduct() {
        // Arrange
        ProductCreateRequest request = new ProductCreateRequest();
        request.setProductName("Test Product");
        Product product = new Product();
        product.setProductName(request.getProductName());
        ProductDto expectedResponse = new ProductDto();
        expectedResponse.setProductName(request.getProductName());

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(expectedResponse);
        // Act
        ProductDto actual = productService.createProduct(request);
        // Assert
        assertNotNull(actual);
        assertEquals(expectedResponse, actual);
        assertEquals("Test Product", actual.getProductName());
        verify(productMapper).toEntity(request);
        verify(productRepository).save(product);
        verify(productMapper).toDto(product);
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
    @Test
    void softDeleteProduct_shouldSetIsActiveFalse_whenProductExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setIsActive(true);

        ProductDto productDto = new ProductDto();

        productDto.setId(productId);
        productDto.setIsActive(false);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        // Act
        ProductDto actual = productService.softDeleteProduct(productId);
        // Assert
        assertNotNull(actual);
        assertFalse(product.getIsActive());
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }
    @Test
    void softDeleteProduct_shouldThrowException_whenProductNotFound() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.softDeleteProduct(productId));
        verify(productRepository).findById(productId);
    }
    @Test
    void getAllProducts_shouldReturnEnhancedProductPojoList() {
        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);

        ProductPojo pojo1 = new ProductPojo();
        pojo1.setId(1L);
        ProductPojo pojo2 = new ProductPojo();
        pojo2.setId(2L);

        ProductImagePOJO image1 = new ProductImagePOJO();
        ProductImagePOJO image2 = new ProductImagePOJO();

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(productMapper.toPojo(product1)).thenReturn(pojo1);
        when(productMapper.toPojo(product2)).thenReturn(pojo2);
        when(productMainImageService.getMainProductImagesByProductId(List.of(1L, 2L)))
                .thenReturn(Map.of(1L, image1, 2L, image2));

        // Act
        List<ProductPojo> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals(image1, result.get(0).getProductImagePOJO());
        assertEquals(image2, result.get(1).getProductImagePOJO());
        verify(productRepository).findAll();
        verify(productMapper, times(2)).toPojo(any(Product.class));
        verify(productMainImageService).getMainProductImagesByProductId(List.of(1L, 2L));
    }
    @Test
    void softRestoreProduct_shouldSetIsActiveTrue_whenProductExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setIsActive(false);

        ProductDto productDto = new ProductDto();
        productDto.setId(productId);
        productDto.setIsActive(true);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);

        // Act
        ProductDto actual = productService.restoreProduct(productId);

        // Assert
        assertNotNull(actual);
        assertTrue(product.getIsActive());
        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }
    @Test
    void restoreProduct_shouldThrowException_whenProductNotFound() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.restoreProduct(productId));
        verify(productRepository).findById(productId);
    }
    @Test
    void getPagedProductByCategoryId_shouldReturnPagedProductPojo() {
        // Arrange
        Long categoryId = 199L;
        int page = 0;
        int size = 2;
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Product product1 = new Product();
        product1.setId(101L);

        Product product2 = new Product();
        product2.setId(102L);

        List<Product> products = List.of(product1, product2);

        ProductPojo pojo1 = new ProductPojo();
        pojo1.setId(101L);

        ProductPojo pojo2 = new ProductPojo();
        pojo2.setId(102L);

        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(productPage);
        when(productMapper.toPojo(product1)).thenReturn(pojo1);
        when(productMapper.toPojo(product2)).thenReturn(pojo2);

        // Act
        Page<ProductPojo> result = productService.getPagedProductByCategoryId(categoryId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals(pojo1.getId(), result.getContent().get(0).getId());
        assertEquals(pojo2.getId(), result.getContent().get(1).getId());

        verify(productRepository).findAllByCategoryId(categoryId, pageable);
        verify(productMapper, times(2)).toPojo(any(Product.class));

    }
    @Test
    void getPagedProductByCategoryId_shouldReturnEmptyPage_whenNoProductsFound() {
        // Arrange
        Long categoryId = 199L;
        int page = 0;
        int size = 2;
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<Product> productPage = new PageImpl<>(List.of(), pageable, 0);

        when(productRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(productPage);

        // Act
        Page<ProductPojo> result = productService.getPagedProductByCategoryId(categoryId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(productRepository).findAllByCategoryId(categoryId, pageable);
    }
    @Test
    void getPagedProductByIsFeaturedTrue_shouldReturnPagedProductPojo() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Product product1 = new Product();
        product1.setId(201L);

        Product product2 = new Product();
        product2.setId(202L);

        List<Product> products = List.of(product1, product2);

        ProductPojo pojo1 = new ProductPojo();
        pojo1.setId(201L);

        ProductPojo pojo2 = new ProductPojo();
        pojo2.setId(202L);

        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());

        when(productRepository.findAllByIsFeaturedTrue(pageable)).thenReturn(productPage);
        when(productMapper.toPojo(product1)).thenReturn(pojo1);
        when(productMapper.toPojo(product2)).thenReturn(pojo2);

        // Act
        Page<ProductPojo> result = productService.getPagedProductByIsFeaturedTrue(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals(pojo1.getId(), result.getContent().get(0).getId());
        assertEquals(pojo2.getId(), result.getContent().get(1).getId());

        verify(productRepository).findAllByIsFeaturedTrue(pageable);
    }
    @Test
    void getPagedProductByIsFeaturedTrue_shouldReturnEmptyPage_whenNoProductsFound() {
        // Arrange
        int page = 0;
        int size = 2;
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<Product> productPage = new PageImpl<>(List.of(), pageable, 0);

        when(productRepository.findAllByIsFeaturedTrue(pageable)).thenReturn(productPage);

        // Act
        Page<ProductPojo> result = productService.getPagedProductByIsFeaturedTrue(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());

        verify(productRepository).findAllByIsFeaturedTrue(pageable);
    }
    @Test
    void findById_shouldReturnProductPojo_whenProductExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        ProductPojo productPojo = new ProductPojo();
        productPojo.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toPojo(product)).thenReturn(productPojo);

        // Act
        Optional<ProductPojo> result = productService.findById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
        verify(productRepository).findById(productId);
        verify(productMapper).toPojo(product);
    }
    @Test
    void findById_shouldReturnEmpty_whenProductNotFound() {
        // Arrange
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Optional<ProductPojo> result = productService.findById(productId);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository).findById(productId);
    }
}