package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.dto.response.ProductDto;
import com.hung.shop.product.entity.Products;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Products entity);
    Products toEntity(ProductDto dto);
    Products toEntity(ProductCreateRequest productCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Products toEntity(ProductUpdateRequest productUpdateRequest, @MappingTarget Products entity);
}
