package com.hung.shop.product.service.impl;

import com.hung.shop.product.exception.product.ProductNotFoundException;
import com.hung.shop.product.service.IProductExistenceChecker;
import com.hung.shop.product.exception.productImage.ProductImageNotFoundException;
import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.dto.productImage.request.ProductImageUpdateRequest;
import com.hung.shop.product.dto.productImage.response.ProductImageDto;
import com.hung.shop.product.entity.ProductImages;
import com.hung.shop.product.mapper.ProductImageMapper;
import com.hung.shop.product.repository.ProductImageRepository;
import com.hung.shop.product.service.IProductImageQueryPort;
import com.hung.shop.product.service.IProductImageService;
import com.hung.shop.product.service.IProductMainImageService;
import com.hung.shop.share.ProductImagePOJO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService, IProductImageQueryPort, IProductMainImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;
    private final IProductExistenceChecker IProductExistenceChecker;


    @Override
    public List<ProductImageDto> getAllProductImages() {
        return productImageRepository.findAll().stream()
                .map(productImageMapper::toDto)
                .toList();
    }
    @Override
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
        if (IProductExistenceChecker.existsById(productId) == false) {
            throw new ProductNotFoundException("Product with id "+productId+" not found");
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
                .orElseThrow(() -> new ProductImageNotFoundException("Product image with id " + id + " not found"));
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