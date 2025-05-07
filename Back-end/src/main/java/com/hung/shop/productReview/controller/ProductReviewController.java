package com.hung.shop.productReview.controller;

import com.hung.shop.productReview.dto.request.ProductReviewCreateRequest;
import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.service.IProductReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
@Tag(name = "Product Review", description = "Product Review API")
public class ProductReviewController {
    @Autowired
    private IProductReviewService productReviewService;
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get all product reviews by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product reviews"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllProductReviewsByProductId(@PathVariable Long productId) {
        try {
            List<ProductReviewDto> productReviews = productReviewService.getAllProductReviewsByProductId(productId);
            return ResponseEntity.ok(productReviews);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while retrieving product reviews: " + e.getMessage());
        }
    }
    @PostMapping("")
    @Operation(summary = "Create a new product review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created product review"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> createProductReview(@RequestBody @Valid ProductReviewCreateRequest productReviewCreateRequest) {
        try {
            ProductReviewDto createdProductReview = productReviewService.createProductReview(productReviewCreateRequest);
            return ResponseEntity.ok(createdProductReview);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating product review: " + e.getMessage());
        }
    }
}
