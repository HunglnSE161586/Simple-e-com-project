package com.hung.shop.product.controller;

import com.hung.shop.product.dto.product.request.ProductCreateRequest;
import com.hung.shop.product.dto.product.request.ProductUpdateRequest;
import com.hung.shop.product.dto.product.response.ProductDetailResponse;
import com.hung.shop.product.dto.product.response.ProductDto;
import com.hung.shop.product.service.IProductService;
import com.hung.shop.share.ProductPojo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product API")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @GetMapping("/all")
    @Operation(summary = "Get all products", description = "Retrieves all products in the system. For testing purposes only. No authentication required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<List<ProductPojo>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("")
    @Operation(summary = "Get paged products", description = "Retrieves paged products ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Page<ProductPojo>> getPagedProducts(@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
                                              @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size,
                                              @RequestParam(required = false) String isFeatured) {
        if (isFeatured != null && isFeatured.equals("true")) {
            return ResponseEntity.ok(productService.getPagedProductByIsFeaturedTrue( page, size));
        }
        return ResponseEntity.ok(productService.getPagedProduct(page, size));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get product by id", description = "Retrieves a product by product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @GetMapping("/categories/{id}")
    @Operation(summary = "Get paginated products by category", description = "Retrieves paginated products by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get products by category successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public ResponseEntity<Page<ProductPojo>> getProductsByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size) {
        return ResponseEntity.ok(productService.getPagedProductByCategoryId(id, page, size));
    }

    @PostMapping("")
    @Operation(summary = "Create a new product", description = "Creates a new product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PreAuthorize("hasRole('ADMIN')" )
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return ResponseEntity.ok(productService.createProduct(productCreateRequest));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product in the system. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PreAuthorize("hasRole('ADMIN')" )
    @SecurityRequirement(name = "Authorization")
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
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PreAuthorize("hasRole('ADMIN')" )
    @SecurityRequirement(name = "Authorization")
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
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PreAuthorize("hasRole('ADMIN')" )
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> restoreProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.restoreProduct(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}