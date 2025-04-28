package com.hung.shop.product.service.impl;

import com.hung.shop.categories.service.ICategoryService;
import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.dto.response.ProductDetailResponse;
import com.hung.shop.product.dto.response.ProductDto;
import com.hung.shop.product.entity.Products;
import com.hung.shop.product.mapper.ProductMapper;
import com.hung.shop.product.repository.ProductRepository;
import com.hung.shop.product.service.IProductService;
import com.hung.shop.productImages.service.IProductImageService;
import com.hung.shop.productReview.service.IProductReviewService;
import com.hung.shop.share.CategoryPOJO;
import com.hung.shop.share.ProductImagePOJO;
import com.hung.shop.share.ProductPojo;
import com.hung.shop.share.ProductReviewPOJO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;
    private final IProductImageService productImageService;
    private final ICategoryService categoryService;
    private final IProductReviewService productReviewService;

    public ProductService(@Lazy IProductImageService productImageService,
                          @Lazy ICategoryService categoryService,
                          @Lazy IProductReviewService productReviewService) {
        this.productImageService = productImageService;
        this.categoryService = categoryService;
        this.productReviewService = productReviewService;
    }

    private List<ProductPojo> getProductPojoWithProductImagePoJo(List<ProductPojo> productPojos) {
        List<Long> productIds = productPojos.stream().map(ProductPojo::getProductId).toList();

        Map<Long, ProductImagePOJO> imageMap = productImageService.getMainProductImagesByProductId(productIds);
        return productPojos.stream()
                .peek(product -> product.setProductImagePOJO(imageMap.get(product.getProductId())))
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
        Products product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        // Get the category POJO, product image POJOs, and product review POJOs
        CategoryPOJO categoryPOJO = categoryService.getCategoryPojoById(product.getCategoryId());
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
