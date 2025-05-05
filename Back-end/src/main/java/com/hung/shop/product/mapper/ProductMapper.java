package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.product.request.ProductCreateRequest;
import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.entity.Product;
import com.hung.shop.share.ProductPojo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product entity);
    Product toEntity(ProductDto dto);
    Product toEntity(ProductCreateRequest productCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product toEntity(ProductUpdateRequest productUpdateRequest, @MappingTarget Product entity);
    ProductPojo toPojo(Product entity);
    @Mapping(target = "categoryPOJO.categoryId", source = "categoryId")
    ProductDetailResponse toDetailResponse(Product entity);
}
