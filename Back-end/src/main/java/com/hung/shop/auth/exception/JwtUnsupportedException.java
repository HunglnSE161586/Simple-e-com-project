package com.hung.shop.auth.exception;

public class JwtUnsupportedException extends InvalidJwtException  {
    public JwtUnsupportedException(String message) {
        super(message);
    }

}
