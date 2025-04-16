package com.hung.shop.services;

import com.hung.shop.dto.respond.CategoryDto;
import com.hung.shop.mapper.CategoryMapper;
import com.hung.shop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDto).toList();
    }
}
