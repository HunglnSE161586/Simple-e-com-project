package com.hung.shop.controllers;

import com.hung.shop.dto.request.UserCreateDto;
import com.hung.shop.dto.respond.UserDto;
import com.hung.shop.entity.CustomUserDetails;
import com.hung.shop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Users", description = "User API")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user with the given credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user already exists")
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        try {
            UserDto userDto=userService.registerUser(userCreateDto);
            return ResponseEntity.ok(userDto);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id", description = "Retrieves a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, current user is not authorized to access other user's information"),
    })
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            // Get the current authenticated user
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Long userIdFromToken = userDetails.getUser().getUserId();

            if (!userIdFromToken.equals(id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access other user's information");
            }
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")
    @Operation(summary = "Get all users", description = "Retrieves all users in the system. This endpoint is for testing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
    })
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
