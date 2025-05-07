package com.hung.shop.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IJwtTokenUtil {
    String generateToken(String email);
    String generateToken(Long userId, String email, Collection<? extends GrantedAuthority> roles);
    String getEmailFromToken(String token);
    boolean validateToken(String token);
    String extractJwtFromRequest(HttpServletRequest request);
    long getTokenExpiryInSeconds(String token);
}
