package com.hung.shop.user.exception;

public class EmailExistedException extends RegisterUserException{
    public EmailExistedException(String message) {
        super(message);
    }
}
