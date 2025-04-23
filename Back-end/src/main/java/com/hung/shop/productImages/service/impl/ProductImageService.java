package com.hung.shop.productImages.service.impl;

import com.hung.shop.product.service.IProductService;
import com.hung.shop.productImages.dto.request.ProductImageCreateRequest;
import com.hung.shop.productImages.dto.request.ProductImageUpdateRequest;
import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.productImages.entity.ProductImages;
import com.hung.shop.productImages.mapper.ProductImageMapper;
import com.hung.shop.productImages.repository.ProductImageRepository;
import com.hung.shop.productImages.service.IProductImageService;
import com.hung.shop.share.ProductImagePOJO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ProductImageMapper productImageMapper;
    private final IProductService productService;
    //@Lazy annotation is used to avoid circular dependency, IProductService is injected only when needed (ex: in createProductImage method)
    public ProductImageService(@Lazy IProductService productService) {
        this.productService = productService;
    }


    public List<ProductImageDto> getAllProductImages() {
        return productImageRepository.findAll().stream()
                .map(productImageMapper::toDto)
                .toList();
    }
    public List<ProductImageDto> getProductImagesByProductId(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(productImage -> productImage.getProductId().equals(productId))
                .map(productImageMapper::toDto)
                .toList();
    }
    @Override
    public List<ProductImagePOJO> getProductImagesPojoByProductId(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(productImage -> productImage.getProductId().equals(productId))
                .map(productImageMapper::toPOJO)
                .toList();
    }

    @Transactional
    @Override
    public void createProductImage(List<ProductImageCreateRequest> productImageCreateRequests, Long productId) {
        // Check if the product exists
        if (productService.getProductById(productId) == null) {
            throw new IllegalArgumentException("Product with id "+productId+" not found");
        }
        for (ProductImageCreateRequest productImageCreateRequest : productImageCreateRequests) {
            ProductImages productImage = productImageMapper.toEntity(productImageCreateRequest);
            productImage.setProductId(productId);
            productImageRepository.save(productImage);
        }
    }

    @Override
    @Transactional
    public ProductImageDto updateProductImage(Long id, ProductImageUpdateRequest productImageUpdateRequest) {
        ProductImages productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product image with id " + id + " not found"));
        return productImageMapper.toDto(productImageRepository.save(
                productImageMapper.toEntity(productImageUpdateRequest, productImage))
        );
    }

    @Override
    public Map<Long, ProductImagePOJO> getMainProductImagesByProductId(List<Long> productIds) {
        List<ProductImages> mainImages = productImageRepository.findMainImagesForProductIds(productIds);

        return mainImages.stream()
                .collect(Collectors.toMap(
                        ProductImages::getProductId,        //key
                        productImageMapper::toPOJO          //value
                ));
    }


}
