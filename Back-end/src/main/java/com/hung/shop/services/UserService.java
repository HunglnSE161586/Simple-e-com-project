package com.hung.shop.services;

import com.hung.shop.dto.request.UserCreateDto;
import com.hung.shop.dto.respond.UserDto;
import com.hung.shop.entity.UserAuth;
import com.hung.shop.entity.UserRoles;
import com.hung.shop.entity.Users;
import com.hung.shop.mapper.UserAuthMapper;
import com.hung.shop.mapper.UserMapper;
import com.hung.shop.repositories.UserRoleRepository;
import com.hung.shop.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final String DEFAULT_ROLE = "ROLE_CUSTOMER";
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public UserDto registerUser(UserCreateDto userCreateDto) {
        // Check if email already exists
        if (usersRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Check if user role exists
        UserRoles defaultRole = userRoleRepository.findByRoleName(DEFAULT_ROLE)
                .orElseThrow(()-> new IllegalArgumentException("Default Role not found"));
        // Create new user
        Users user = userMapper.toEntity(userCreateDto);
        user.setRole(defaultRole);
        // Set password
        String password = passwordEncoder.encode(userCreateDto.getUserAuthCreateDto().getPassword());
        userCreateDto.getUserAuthCreateDto().setPassword(password);
        UserAuth userAuth = userAuthMapper.toEntity(userCreateDto.getUserAuthCreateDto());
        userAuth.setUser(user);
        user.setUserAuths(new ArrayList<>());
        user.getUserAuths().add(userAuth);
    
        user= usersRepository.save(user);
        return userMapper.toDto(user);
    }
    public UserDto getUserById(Long id) {
        Optional<Users> userOptional = usersRepository.findByUserId(id);
        if (userOptional.isPresent()) {
            return userMapper.toDto(userOptional.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = usersRepository.findAll().stream().map(userMapper::toDto).toList();
        return userDtos;
    }
}
