package com.hung.shop.auth.controller;

import com.hung.shop.auth.dto.request.LoginRequest;
import com.hung.shop.auth.entity.CustomUserDetails;
import com.hung.shop.auth.service.IJwtBlacklistService;
import com.hung.shop.auth.utils.IJwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Auths", description = "Authentication API")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IJwtTokenUtil jwtTokenUtil;
    private final IJwtBlacklistService blacklistService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    public ResponseEntity<String> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
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
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Generate JWT token for the authenticated user
            String token = jwtTokenUtil.generateToken(userDetails.getUser().getId(),loginRequest.username, authentication.getAuthorities());

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

        String token = jwtTokenUtil.extractJwtFromRequest(request);
        if (token != null) {
            long expiry = jwtTokenUtil.getTokenExpiryInSeconds(token); // Parse JWT to get remaining life
            blacklistService.blacklistToken(token, expiry);
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(400).body("No token found in the request. Please provide a valid token.");
    }
}
