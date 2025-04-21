package com.hung.shop.categories.service.impl;

import com.hung.shop.categories.dto.response.CategoryDto;
import com.hung.shop.categories.mapper.CategoryMapper;
import com.hung.shop.categories.repository.CategoryRepository;
import com.hung.shop.categories.service.ICategoryService;
import com.hung.shop.share.CategoryPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDto).toList();
    }

    @Override
    public List<CategoryPOJO> getAllCategoriesPOJO() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryPOJO)
                .toList();
    }

    @Override
    public CategoryPOJO getCategoryPojoById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryPOJO)
                .orElse(null);
    }

    @Override
    public Page<CategoryDto> getAllCategories(int page, int size, String sortBy, String sortDir) {
        return categoryRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy)))
                .map(categoryMapper::toCategoryDto);
    }

}
