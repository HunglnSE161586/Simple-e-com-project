package com.hung.shop.product.controller;

import com.hung.shop.product.dto.productImage.request.ProductImageCreateRequest;
import com.hung.shop.product.dto.productImage.request.ProductImageUpdateRequest;
import com.hung.shop.product.dto.productImage.response.ProductImageDto;
import com.hung.shop.product.service.IProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@Tag(name = "Product Images", description = "Product Images API")
@RequiredArgsConstructor
public class ProductImageController {
    private final IProductImageService productImageService;
    @GetMapping
    @Operation(summary = "Get all product images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product images retrieved successfully"),
    })
    public List<ProductImageDto> getAllProductImages() {
        return productImageService.getAllProductImages();
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get product images by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product images retrieved successfully"),
    })
    public List<ProductImageDto> getProductImagesByProductId(@PathVariable Long productId) {
        return productImageService.getProductImagesByProductId(productId);
    }
    @PostMapping("/product/{productId}")
    @Operation(summary = "Create product images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product images created successfully"),
    })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<String> createProductImage(@RequestBody List<ProductImageCreateRequest> productImageCreateRequests, @PathVariable Long productId) {
        productImageService.createProductImage(productImageCreateRequests, productId);
        return ResponseEntity.ok("Product images created successfully");
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update product image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product image updated successfully"),
    })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ProductImageDto> updateProductImage(@PathVariable Long id, @RequestBody ProductImageUpdateRequest productImageUpdateRequest) {
        return ResponseEntity.ok(productImageService.updateProductImage(id, productImageUpdateRequest));
    }
}
