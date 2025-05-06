package com.hung.shop.product.controller;

import com.hung.shop.product.dto.category.request.CategoryCreateRequest;
import com.hung.shop.product.dto.category.request.CategoryUpdateRequest;
import com.hung.shop.product.dto.category.response.CategoryDto;
import com.hung.shop.product.service.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category API")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    @GetMapping("/all-test")
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the system. For testing purposes only. No authentication required.")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("")
    @Operation(summary = "Get all categories with pagination", description = "Retrieves all categories in the system with pagination.")
    public ResponseEntity<Page<CategoryDto>> getAllCategoriesPaged(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
                                                                   @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size,
                                                                   @RequestParam(defaultValue = "categoryName") String sortBy,
                                                                   @RequestParam(defaultValue = "ASC") String sortDir) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size, sortBy, sortDir));
    }
    @PostMapping()
    @Operation(summary = "Create a new category", description = "Creates a new category in the system.")
    @PreAuthorize("hasRole('ADMIN')" )
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryCreateRequest));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')" )
    @Operation(summary = "Update an existing category", description = "Updates an existing category in the system.")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryUpdateRequest));
    }
}