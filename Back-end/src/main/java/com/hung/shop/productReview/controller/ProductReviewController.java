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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-reviews")
@Tag(name = "Product Review", description = "Product Review API")
@RequiredArgsConstructor
public class ProductReviewController {
    private final IProductReviewService productReviewService;

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get all product reviews by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product reviews"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductReviewDto>> getAllProductReviewsByProductId(@PathVariable Long productId) {
        List<ProductReviewDto> productReviews = productReviewService.getAllProductReviewsByProductId(productId);
        return ResponseEntity.ok(productReviews);
    }

    @PostMapping("")
    @Operation(summary = "Create a new product review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created product review"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @SecurityRequirement(name = "Authorization")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<?> createProductReview(@RequestBody @Valid ProductReviewCreateRequest productReviewCreateRequest) {
        ProductReviewDto createdProductReview = productReviewService.createProductReview(productReviewCreateRequest);
        return ResponseEntity.ok(createdProductReview);
    }
}
