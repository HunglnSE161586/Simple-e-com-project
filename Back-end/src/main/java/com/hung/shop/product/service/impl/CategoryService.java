package com.hung.shop.product.service.impl;

import com.hung.shop.product.exception.category.CategoryNotFoundException;
import com.hung.shop.product.dto.category.request.CategoryCreateRequest;
import com.hung.shop.product.dto.category.request.CategoryUpdateRequest;
import com.hung.shop.product.dto.category.response.CategoryDto;
import com.hung.shop.product.entity.Category;
import com.hung.shop.product.mapper.CategoryMapper;
import com.hung.shop.product.repository.CategoryRepository;
import com.hung.shop.product.service.ICategoryService;
import com.hung.shop.share.CategoryPOJO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
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

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryCreateRequest categoryCreateRequest) {
        return categoryMapper.toCategoryDto(
                categoryRepository.save(categoryMapper.toEntity(categoryCreateRequest))
        );
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return categoryMapper.toCategoryDto(
                categoryRepository.save(categoryMapper.toEntity(categoryUpdateRequest, category))
        );
    }

}