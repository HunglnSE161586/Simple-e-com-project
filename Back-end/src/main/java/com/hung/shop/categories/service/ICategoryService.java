package com.hung.shop.categories.service;

import com.hung.shop.categories.dto.request.CategoryCreateRequest;
import com.hung.shop.categories.dto.request.CategoryUpdateRequest;
import com.hung.shop.categories.dto.response.CategoryDto;
import com.hung.shop.share.CategoryPOJO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAllCategories();
    List<CategoryPOJO> getAllCategoriesPOJO();
    CategoryPOJO getCategoryPojoById(Long id);
    Page<CategoryDto> getAllCategories(int page, int size, String sortBy, String sortDir);
    CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest);
    CategoryDto updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest);
}
