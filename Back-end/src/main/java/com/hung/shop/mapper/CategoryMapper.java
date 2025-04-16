package com.hung.shop.mapper;

import com.hung.shop.dto.respond.CategoryDto;
import com.hung.shop.entity.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Categories categories);
}
