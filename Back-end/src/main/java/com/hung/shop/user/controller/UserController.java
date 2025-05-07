package com.hung.shop.user.controller;

import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import com.hung.shop.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User API")
public class UserController {
    @Autowired
    private IUserService userService;
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
            /* Catching Exception is too broad. Catch specific ones instead
            *  Use @ControllerAdvice for global exception handling*/
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
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
    // has warning when run:
    //Serializing PageImpl instances as-is is not supported, meaning that there is no guarantee about the stability of the resulting JSON structure!
    @GetMapping("")
    @Operation(summary = "Get paged users", description = "Retrieves users in the system with paging.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> getPagedUsers( @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative") int page,
                                            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be at least 1") int size) {
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a user", description = "Marks a user as inactive instead of deleting from database. Only  an admin can perform this operation.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User soft-deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found"),
    })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> softDeleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.updateUserStatus(id, false)); // false = inactive
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PatchMapping("/{id}/restore")
    @Operation(summary = "Restore a soft-deleted user", description = "Restores a soft-deleted user. Only the user or an admin can perform this operation.")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User restored successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or user not found")
    })
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<?> restoreUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.updateUserStatus(id, true)); // true = active
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}