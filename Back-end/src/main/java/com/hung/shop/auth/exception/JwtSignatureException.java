package com.hung.shop.auth.exception;

import java.security.SignatureException;

public class JwtSignatureException extends InvalidJwtException  {
    public JwtSignatureException(String message) {
        super(message);
    }

}
