package com.hung.shop.auth.exception;

public class JwtBlacklistException extends InvalidJwtException {
    public JwtBlacklistException(String message) {
        super(message);
    }
}
