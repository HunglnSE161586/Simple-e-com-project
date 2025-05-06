package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.dto.productImage.request.ProductImageUpdateRequest;
import com.hung.shop.product.dto.productImage.response.ProductImageDto;
import com.hung.shop.product.entity.ProductImage;
import com.hung.shop.share.ProductImagePOJO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductImageMapper{
    @Mapping(target = "productId", source = "productImage.product.id")
    ProductImageDto toDto(ProductImage productImage);
    @Mapping(target = "productId", source = "productImage.product.id")
    ProductImagePOJO toPOJO(ProductImage productImage);
    ProductImage toEntity(ProductImageCreateRequest productImageCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductImage toEntity(ProductImageUpdateRequest productImageUpdateRequest, @MappingTarget ProductImage productImage);
}
