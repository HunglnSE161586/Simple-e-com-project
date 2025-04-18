package com.hung.shop.productImages.controller;

import com.hung.shop.productImages.dto.response.ProductImageDto;
import com.hung.shop.productImages.service.IProductImageService;
import com.hung.shop.productImages.service.impl.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@Tag(name = "Product Images", description = "Product Images API")
public class ProductImageController {
    @Autowired
    private IProductImageService productImageService;
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
}
