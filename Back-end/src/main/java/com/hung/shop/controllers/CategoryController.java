package com.hung.shop.controllers;

import com.hung.shop.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category API")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the system. For testing purposes only. No authentication required.")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
