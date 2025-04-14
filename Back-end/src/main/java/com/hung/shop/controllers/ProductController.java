package com.hung.shop.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Products", description = "Product API")
public class ProductController {
    @GetMapping()
    public String getAllProducts() {
        return "List of products";
    }
}
