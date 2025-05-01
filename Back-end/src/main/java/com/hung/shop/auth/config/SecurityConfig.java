package com.hung.shop.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hung.shop.auth.config.JwtAuthenticationFilter;
import com.hung.shop.auth.service.impl.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig{
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF since we're using token-based authentication
                .csrf(csrf -> csrf.disable())
                // Make the session stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Permit public endpoints for login and logout
                        .requestMatchers("api/auth/login",
                                "api/auth/logout",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/index.html",
                                "/api/users/**",
                                "/api/products/**",
                                "/api/categories/**",
                                "/api/product-reviews/**",
                                "/api/product-images/**",
                                "/api-docs/**").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");

                            Map<String, Object> errorDetails = new HashMap<>();
                            errorDetails.put("status", 401);
                            errorDetails.put("error", "Unauthorized");
                            errorDetails.put("message", "JWT token is missing or invalid");
                            errorDetails.put("path", request.getRequestURI());

                            new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");

                            Map<String, Object> errorDetails = new HashMap<>();
                            errorDetails.put("status", 403);
                            errorDetails.put("error", "Forbidden");
                            errorDetails.put("message", "You don't have permission to access this resource");
                            errorDetails.put("path", request.getRequestURI());

                            new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
                        })
                )

                // Optionally configure exception handling or CORS if needed
                .httpBasic(Customizer.withDefaults());

        // Add your custom JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L); // 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
