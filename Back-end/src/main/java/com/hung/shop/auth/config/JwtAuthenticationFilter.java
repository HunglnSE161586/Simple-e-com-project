package com.hung.shop.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.shop.auth.exception.InvalidJwtException;
import com.hung.shop.auth.exception.JwtBlacklistException;
import com.hung.shop.auth.service.IJwtBlacklistService;
import com.hung.shop.auth.utils.IJwtTokenUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.HashMap;
import java.util.Map;

// remove @Component annotation to avoid circular dependency
// @Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private IJwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IJwtBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, java.io.IOException {
        try {
            String jwt = jwtTokenUtil.extractJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
                if (blacklistService.isBlacklisted(jwt)) {
                    logger.warn("Token is blacklisted: {}", jwt);
                    throw new JwtBlacklistException("Token has been blacklisted");
                }
                String email = jwtTokenUtil.getEmailFromToken(jwt);

                // Load user details based on the email
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Create an authentication token and set it in the context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (InvalidJwtException ex) {
            logger.warn("JWT authentication failed: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("status", 401);
            responseBody.put("error", "Unauthorized");
            responseBody.put("message", ex.getMessage());
            responseBody.put("path", request.getRequestURI());

            new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
            return; // Stop further processing
        }
        filterChain.doFilter(request, response);
    }

}
