package com.hung.shop.categories.mapper;

import com.hung.shop.categories.dto.response.CategoryDto;
import com.hung.shop.categories.entity.Categories;
import com.hung.shop.share.CategoryPOJO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Categories categories);
    CategoryPOJO toCategoryPOJO(Categories categories);
}
