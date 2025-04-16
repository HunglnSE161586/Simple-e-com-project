package com.hung.shop.controllers;

import com.hung.shop.dto.request.LoginRequest;
import com.hung.shop.services.CustomUserDetailsService;
import com.hung.shop.services.JwtBlacklistService;
import com.hung.shop.services.UserService;
import com.hung.shop.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auths", description = "Authentication API")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtBlacklistService blacklistService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // spring check user exists by calling CustomUserDetailsService.loadUserByUsername()
            // it also auto hash password and check with the password in the database
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);


            // Generate JWT token for the authenticated user
            String token = jwtTokenUtil.generateToken(loginRequest.username);

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Logout user and blacklist the token. Note: test with Postman." +
            " Do not use Swagger to test this API as Swagger won't send the token in the header.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "No token found in the request. Please provide a valid token.")
    })
    public ResponseEntity<?> logout(HttpServletRequest request) {
        System.out.println("Auth Header: " + request.getHeader("Authorization"));
        System.out.println("Extracted token: " + jwtTokenUtil.extractJwtFromRequest(request));

        String token = jwtTokenUtil.extractJwtFromRequest(request);
        if (token != null) {
            long expiry = jwtTokenUtil.getTokenExpiryInSeconds(token); // Parse JWT to get remaining life
            blacklistService.blacklistToken(token, expiry);
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(400).body("No token found in the request. Please provide a valid token.");
    }
}
