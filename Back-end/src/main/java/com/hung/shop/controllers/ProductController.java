package com.hung.shop.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "Authorization")
public class ProductController {
    @GetMapping()
    public String getAllProducts() {
        return "List of products";
    }
}
