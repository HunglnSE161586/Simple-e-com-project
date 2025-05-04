package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.product.request.ProductCreateRequest;
import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.entity.Products;
import com.hung.shop.share.ProductPojo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Products entity);
    Products toEntity(ProductDto dto);
    Products toEntity(ProductCreateRequest productCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Products toEntity(ProductUpdateRequest productUpdateRequest, @MappingTarget Products entity);
    ProductPojo toPojo(Products entity);
    @Mapping(target = "categoryPOJO.categoryId", source = "categoryId")
    ProductDetailResponse toDetailResponse(Products entity);
}
