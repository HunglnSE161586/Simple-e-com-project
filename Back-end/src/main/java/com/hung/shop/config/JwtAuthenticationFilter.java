package com.hung.shop.config;

import com.hung.shop.services.CustomUserDetailsService;
import com.hung.shop.services.JwtBlacklistService;
import com.hung.shop.utils.JwtTokenUtil;
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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtBlacklistService blacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws jakarta.servlet.ServletException, java.io.IOException {
        try {
            String jwt = jwtTokenUtil.extractJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
                if (blacklistService.isBlacklisted(jwt)) {
                    throw new JwtException("Token has been blacklisted");
                }
                String email = jwtTokenUtil.getUsernameFromToken(jwt);

                // Load user details based on the email
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Create an authentication token and set it in the context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            // Log the exception or handle errors as needed
            logger.warn("JWT authentication failed: {}", ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
