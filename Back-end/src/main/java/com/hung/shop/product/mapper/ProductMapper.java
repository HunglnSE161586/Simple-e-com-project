package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.response.ProductDto;
import com.hung.shop.product.entity.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Products entity);
}
