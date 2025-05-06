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
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product entity);
    Product toEntity(ProductCreateRequest productCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductUpdateRequest productUpdateRequest, @MappingTarget Product entity);
    @Mapping(target = "categoryId", source = "category.id")
    ProductPojo toPojo(Product entity);
    @Mapping(target = "categoryPOJO.id", source = "category.id")
    ProductDetailResponse toDetailResponse(Product entity);
}
