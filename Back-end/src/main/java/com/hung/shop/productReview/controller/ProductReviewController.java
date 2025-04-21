package com.hung.shop.productReview.controller;

import com.hung.shop.productReview.dto.response.ProductReviewDto;
import com.hung.shop.productReview.service.IProductReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
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
}
