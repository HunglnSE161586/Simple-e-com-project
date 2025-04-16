package com.hung.shop.mapper;

import com.hung.shop.dto.respond.ProductImageDto;
import com.hung.shop.entity.ProductImages;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper{
    ProductImageDto toDto(ProductImages productImage);
}
