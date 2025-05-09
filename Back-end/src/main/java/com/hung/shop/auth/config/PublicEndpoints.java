package com.hung.shop.auth.config;

import org.springframework.http.HttpMethod;

import java.util.List;

public class PublicEndpoints {
    public static final List<Endpoint> PUBLIC_ENDPOINTS = List.of(
            new Endpoint(HttpMethod.GET, "/api/products/**"),
            new Endpoint(HttpMethod.GET, "/api/categories/**"),
            new Endpoint(HttpMethod.GET, "/api/product-images/**"),
            new Endpoint(HttpMethod.POST, "/api/auth/login"),
            new Endpoint(null, "/api/auth/logout"),
            new Endpoint(null, "/swagger-ui/**"),
            new Endpoint(null, "/v3/api-docs/**"),
            new Endpoint(null, "/swagger-ui/index.html"),
            new Endpoint(null, "/api/product-reviews/**"),
            new Endpoint(null, "/api/users/**"),
            new Endpoint(null, "/api-docs/**")
    );

    public record Endpoint(HttpMethod method, String pattern) {}
}
