package com.hung.shop.productReview.exception;

public class ProductNotExistException extends ReviewCreateException {
    public ProductNotExistException(String message) {
        super(message);
    }
}
