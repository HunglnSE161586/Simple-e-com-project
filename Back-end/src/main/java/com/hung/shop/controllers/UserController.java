package com.hung.shop.controllers;

import com.hung.shop.dto.request.UserCreateDto;
import com.hung.shop.dto.respond.UserDto;
import com.hung.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserDto userDto=userService.registerUser(userCreateDto);
            return ResponseEntity.ok(userDto);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
