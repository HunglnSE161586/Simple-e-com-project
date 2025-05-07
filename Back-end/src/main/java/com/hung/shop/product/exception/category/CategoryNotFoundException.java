package com.hung.shop.product.exception.category;

public class CategoryNotFoundException extends NullPointerException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
