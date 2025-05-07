package com.hung.shop.auth.exception;

public class JwtExpiredException extends InvalidJwtException  {
    public JwtExpiredException(String message) {
        super(message);
    }

}
