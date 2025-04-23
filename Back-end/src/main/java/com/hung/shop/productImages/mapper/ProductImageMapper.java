package com.hung.shop.productImages.mapper;

import com.hung.shop.productImages.dto.request.ProductImageCreateRequest;
import com.hung.shop.productImages.dto.request.ProductImageUpdateRequest;
import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.productImages.entity.ProductImages;
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
