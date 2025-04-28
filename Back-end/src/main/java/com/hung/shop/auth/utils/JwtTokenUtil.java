package com.hung.shop.auth.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
@Component
public class JwtTokenUtil implements IJwtTokenUtil {
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("tempSecretKey1234567890aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());

    // Token validity in milliseconds (e.g., 15 minutes)
    private final long jwtExpirationInMs = 900000; // 15*60*1000

    // Generate JWT token based on the user's username
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }
    public String generateToken(Long userId, String email, Collection<? extends GrantedAuthority> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId) // Add userId as a claim
                .claim("role", roles) // Add role as a claim
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    // Extract email from token
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
    public String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public long getTokenExpiryInSeconds(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY).build()
                .parseSignedClaims(token)
                .getPayload();

        return (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
    }
}
