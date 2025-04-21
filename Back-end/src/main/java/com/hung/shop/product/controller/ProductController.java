package com.hung.shop.product.controller;

import com.hung.shop.product.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
