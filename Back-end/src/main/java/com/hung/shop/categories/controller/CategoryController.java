package com.hung.shop.categories.controller;

import com.hung.shop.categories.dto.request.CategoryCreateRequest;
import com.hung.shop.categories.dto.request.CategoryUpdateRequest;
import com.hung.shop.categories.service.ICategoryService;
import com.hung.shop.categories.service.impl.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping()
    @Operation(summary = "Create a new category", description = "Creates a new category in the system.")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(categoryCreateRequest));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category", description = "Updates an existing category in the system.")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, categoryUpdateRequest));
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body("Category not found");
        }
    }
}
