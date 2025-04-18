package com.hung.shop.categories.controller;

import com.hung.shop.categories.service.ICategoryService;
import com.hung.shop.categories.service.impl.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category API")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping()
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the system. For testing purposes only. No authentication required.")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/paged")
    @Operation(summary = "Get all categories with pagination", description = "Retrieves all categories in the system with pagination.")
    public ResponseEntity<?> getAllCategoriesPaged(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
                                                   @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size,
                                                   @RequestParam(defaultValue = "categoryName") String sortBy,
                                                   @RequestParam(defaultValue = "ASC") String sortDir) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size, sortBy, sortDir));
    }
}
