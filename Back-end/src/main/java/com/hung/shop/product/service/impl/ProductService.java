package com.hung.shop.product.service.impl;

import com.hung.shop.product.exception.product.ProductNotFoundException;
import com.hung.shop.product.service.ICategoryService;
import com.hung.shop.product.dto.product.request.ProductCreateRequest;
import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.entity.Product;
import com.hung.shop.product.mapper.ProductMapper;
import com.hung.shop.product.repository.ProductRepository;
import com.hung.shop.product.service.IProductService;
import com.hung.shop.product.service.IProductImageQueryPort;
import com.hung.shop.product.service.IProductMainImageService;
import com.hung.shop.productReview.service.IProductReviewService;
import com.hung.shop.share.CategoryPOJO;
import com.hung.shop.share.ProductImagePOJO;
import com.hung.shop.share.ProductPojo;
import com.hung.shop.share.ProductReviewPOJO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final String PRODUCT_NOT_FOUND = "Product not found, id:";
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final IProductImageQueryPort productImageService;
    private final IProductMainImageService productMainImageService;
    private final ICategoryService categoryService;
    private final IProductReviewService productReviewService;


    private List<ProductPojo> getProductPojoWithProductImagePoJo(List<ProductPojo> productPojos) {
        List<Long> productIds = productPojos.stream().map(ProductPojo::getId).toList();

        Map<Long, ProductImagePOJO> imageMap = productMainImageService.getMainProductImagesByProductId(productIds);
        return productPojos.stream()
                .peek(product -> product.setProductImagePOJO(imageMap.get(product.getId())))
                .toList();
    }

    @Override
    public List<ProductPojo> getAllProducts() {
        List<ProductPojo> productPojos= productRepository.findAll().stream().map(productMapper::toPojo).toList();
        return getProductPojoWithProductImagePoJo(productPojos);
    }
    @Override
    public ProductDetailResponse getProductById(Long id) {
        // Get the product by id
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        // Get the category POJO, product image POJOs, and product review POJOs
        CategoryPOJO categoryPOJO = categoryService.getCategoryPojoById(product.getCategory().getId());
        List<ProductImagePOJO> productImagePOJOS = productImageService.getProductImagesPojoByProductId(id);
        List<ProductReviewPOJO> productReviewPOJOS = productReviewService.getProductReviewsPojoByProductId(id);
        // Map product to ProductDetailResponse
        ProductDetailResponse productDetailResponse = productMapper.toDetailResponse(product);
        // Set the product image POJOs, product review POJOs, and category POJO
        productDetailResponse.setCategoryPOJO(categoryPOJO);
        productDetailResponse.setProductImagePOJOS(productImagePOJOS);
        productDetailResponse.setProductReviewPOJOS(productReviewPOJOS);
        //
        return productDetailResponse;
    }
    @Override
    public Page<ProductPojo> getPagedProduct(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        // Get the product POJOs
        Page<ProductPojo> productPage = productRepository.findAll(pageable).map(productMapper::toPojo);
        // Get the product image POJOs
        List<ProductPojo> productPojos = getProductPojoWithProductImagePoJo(productPage.getContent());
        return new PageImpl<>(productPojos, pageable, productPage.getTotalElements());
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductCreateRequest productCreateRequest) {
        Product product = productMapper.toEntity(productCreateRequest);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productUpdateRequest, product)));
    }

    @Override
    @Transactional
    public ProductDto softDeleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        product.setIsActive(false);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductDto restoreProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
        product.setIsActive(true);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Optional<ProductPojo> findById(Long id) {
        return productRepository.findById(id).map(productMapper::toPojo);
    }

    @Override
    public Page<ProductPojo> getPagedProductByCategoryId(Long categoryId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<ProductPojo> productPage = productRepository.findAllByCategoryId(categoryId, pageable).map(productMapper::toPojo);
        // Get the product image POJOs
        List<ProductPojo> productPojos = getProductPojoWithProductImagePoJo(productPage.getContent());
        return new PageImpl<>(productPojos, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductPojo> getPagedProductByIsFeaturedTrue(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        // Get the product POJOs
        Page<ProductPojo> productPage = productRepository.findAllByIsFeaturedTrue(pageable).map(productMapper::toPojo);
        // Get the product image POJOs
        List<ProductPojo> productPojos = getProductPojoWithProductImagePoJo(productPage.getContent());
        return new PageImpl<>(productPojos, pageable, productPage.getTotalElements());
    }
}