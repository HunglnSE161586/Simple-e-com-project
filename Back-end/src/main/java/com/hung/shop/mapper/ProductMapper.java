package com.hung.shop.mapper;

import com.hung.shop.dto.respond.ProductDto;
import com.hung.shop.entity.Products;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Products entity);
}
