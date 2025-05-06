package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.dto.productImage.request.ProductImageUpdateRequest;
import com.hung.shop.product.dto.productImage.response.ProductImageDto;
import com.hung.shop.product.entity.ProductImages;
import com.hung.shop.share.ProductImagePOJO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductImageMapper{
    ProductImageDto toDto(ProductImages productImage);
    ProductImagePOJO toPOJO(ProductImages productImage);
    ProductImages toEntity(ProductImageDto productImageDto);
    ProductImages toEntity(ProductImagePOJO productImagePOJO);
    ProductImages toEntity(ProductImageCreateRequest productImageCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductImages toEntity(ProductImageUpdateRequest productImageUpdateRequest,@MappingTarget ProductImages productImage);
}
