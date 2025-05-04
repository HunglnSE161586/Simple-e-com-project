package com.hung.shop.categories;

public class CategoryNotFoundException extends NullPointerException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
