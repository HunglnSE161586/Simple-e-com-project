package com.hung.shop.categories.mapper;

import com.hung.shop.categories.dto.request.CategoryCreateRequest;
import com.hung.shop.categories.dto.request.CategoryUpdateRequest;
import com.hung.shop.categories.dto.response.CategoryDto;
import com.hung.shop.categories.entity.Categories;
import com.hung.shop.share.CategoryPOJO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Categories categories);
    CategoryPOJO toCategoryPOJO(Categories categories);
    Categories toEntity(CategoryCreateRequest categoryCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Categories toEntity(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Categories categories);
}
