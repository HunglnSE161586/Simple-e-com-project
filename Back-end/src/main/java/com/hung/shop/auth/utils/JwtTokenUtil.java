package com.hung.shop.auth.utils;

import com.hung.shop.GlobalExceptionHandler;
import com.hung.shop.auth.exception.JwtExpiredException;
import com.hung.shop.auth.exception.JwtMalformedException;
import com.hung.shop.auth.exception.JwtSignatureException;
import com.hung.shop.auth.exception.JwtUnsupportedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
@Component
public class JwtTokenUtil implements IJwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public JwtTokenUtil(@Value("${app.jwt.secret-key}") String secretKeyString) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }
    private final SecretKey SECRET_KEY;

    // Token validity in milliseconds (e.g., 15 minutes)
    private final long jwtExpirationInMs = 900000; // 15*60*1000

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
                .claim("userId", userId)
                .claim("role", roles)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException ex) {
            logger.warn("Invalid JWT signature: {}", ex.getMessage());
            throw new JwtSignatureException("Invalid JWT signature"+ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.warn("Invalid JWT token: {}", ex.getMessage());
            throw new JwtMalformedException("Invalid JWT token:"+ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT token is expired: {}", ex.getMessage());
            throw new JwtExpiredException("Expired JWT token:"+ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.warn("Unsupported JWT token: {}", ex.getMessage());
            throw new JwtUnsupportedException("Unsupported JWT token:"+ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.warn("JWT claims string is empty: {}", ex.getMessage());
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
