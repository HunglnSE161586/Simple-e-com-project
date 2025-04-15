package com.hung.shop.controllers;

import com.hung.shop.dto.request.UserCreateRequest;
import com.hung.shop.dto.request.UserUpdateRequest;
import com.hung.shop.dto.respond.UserDto;
import com.hung.shop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        try {
            UserDto userDto=userService.registerUser(userCreateRequest);
            return ResponseEntity.ok(userDto);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id", description = "Retrieves a user by their ID.")
    @PreAuthorize("#id == authentication.principal.user.userId or hasRole('ADMIN')")    // Allow access to the user themselves or an admin
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, current user is not authorized to access other user's information"),
    })
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
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
    @GetMapping("")
    @Operation(summary = "Get paged users", description = "Retrieves users in the system with paging.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPagedUsers( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        try {
            return ResponseEntity.ok(userService.getPagedUsers(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a user by id", description = "Updates a user by their ID. Only the user themselves or an admin can update the user information. No password update is allowed, yet.")
    @PreAuthorize("#id == authentication.principal.user.userId or hasRole('ADMIN')")    // Allow access to the user themselves or an admin
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden, current user is not authorized to update other user's information"),
    })
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
