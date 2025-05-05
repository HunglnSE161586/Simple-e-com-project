package com.hung.shop.product.exception.product;

public class ProductNotFoundException extends NullPointerException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
