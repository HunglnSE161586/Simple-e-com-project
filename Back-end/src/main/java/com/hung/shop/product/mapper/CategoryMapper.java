package com.hung.shop.product.mapper;

import com.hung.shop.product.dto.category.request.CategoryCreateRequest;
import com.hung.shop.product.dto.category.request.CategoryUpdateRequest;
import com.hung.shop.product.dto.category.response.CategoryDto;
import com.hung.shop.product.entity.Category;
import com.hung.shop.share.CategoryPOJO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    CategoryPOJO toCategoryPOJO(Category category);
    Category toEntity(CategoryCreateRequest categoryCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Category toEntity(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);
}
