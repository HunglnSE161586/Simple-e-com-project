package com.hung.shop.auth.exception;

public class JwtMalformedException extends InvalidJwtException  {
    public JwtMalformedException(String message) {
        super(message);
    }

}
