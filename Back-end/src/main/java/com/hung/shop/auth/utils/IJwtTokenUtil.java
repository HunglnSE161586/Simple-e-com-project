package com.hung.shop.auth.utils;

import jakarta.servlet.http.HttpServletRequest;

public interface IJwtTokenUtil {
    String generateToken(String email);
    String getEmailFromToken(String token);
    boolean validateToken(String token);
    String extractJwtFromRequest(HttpServletRequest request);
    long getTokenExpiryInSeconds(String token);
}
