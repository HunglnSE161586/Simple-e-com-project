package com.hung.shop.productReview.exception;

public class UserNotExistException extends ReviewCreateException {
    public UserNotExistException(String message) {
        super(message);
    }
}
