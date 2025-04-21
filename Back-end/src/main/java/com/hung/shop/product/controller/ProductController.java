package com.hung.shop.product.controller;

import com.hung.shop.product.dto.request.ProductCreateRequest;
import com.hung.shop.product.dto.request.ProductUpdateRequest;
import com.hung.shop.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Products", description = "Product API")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping()
    @Operation(summary = "Get all products", description = "Retrieves all products in the system. For testing purposes only. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
    })
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/paged")
    @Operation(summary = "Get paged products", description = "Retrieves paged products ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
    })
    public ResponseEntity<?> getPagedProducts(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
                                              @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size) {
        return ResponseEntity.ok(productService.getPagedProduct(page, size));
    }
    @PostMapping("")
    @Operation(summary = "Create a new product", description = "Creates a new product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
    })
    public ResponseEntity<?> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        try {
            return ResponseEntity.ok(productService.createProduct(productCreateRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID or request body"),
    })
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productUpdateRequest));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a product", description = "Soft deletes a product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product soft deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
    })
    public ResponseEntity<?> softDeleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.softDeleteProduct(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/{id}/restore")
    @Operation(summary = "Restore a soft-deleted product", description = "Restores a soft-deleted product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product restored successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID"),
    })
    public ResponseEntity<?> restoreProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.restoreProduct(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
