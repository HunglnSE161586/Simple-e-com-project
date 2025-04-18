package com.hung.shop.user.service;

import com.hung.shop.auth.service.IUserAuthService;
import com.hung.shop.userRole.service.IUserRoleService;
import com.hung.shop.share.UserRolePOJO;
import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import com.hung.shop.user.entity.Users;
import com.hung.shop.user.internal.IUserService;
import com.hung.shop.user.mapper.UserMapper;
import com.hung.shop.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final String DEFAULT_ROLE = "ROLE_CUSTOMER";
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IUserAuthService userAuthService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public UserDto registerUser(UserCreateRequest userCreateRequest) {
        // Check if email already exists
        if (usersRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Check if user role exists
        UserRolePOJO defaultRole = userRoleService.findByRoleName(DEFAULT_ROLE)
                .orElseThrow(()-> new IllegalArgumentException("Default Role not found"));
        // Create new user
        Users user = userMapper.userCreateRequestToEntity(userCreateRequest);
        user.setRoleId(defaultRole.getRoleId());
        // save user
        user= usersRepository.save(user);
        // Set, save password
        String password = passwordEncoder.encode(userCreateRequest.getPassword());
        userAuthService.saveUserAuth(user.getUserId(), password);
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
    public Page<UserDto> getPagedUsers(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<UserDto> userDtos = usersRepository.findAll(pageable).map(userMapper::toDto);
        return userDtos;
    }
    @Transactional
    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        Optional<Users> userOptional = usersRepository.findByUserId(id);
        if (userOptional.isPresent()) {
            return userMapper.toDto(usersRepository.save(
                    userMapper.userUpdateRequestToEntity(userUpdateRequest, userOptional.get())
            ));
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    @Transactional
    public UserDto updateUserStatus(Long id, Boolean isActive) {
        Optional<Users> userOptional = usersRepository.findByUserId(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setIsActive(isActive);
                return userMapper.toDto(usersRepository.save(user));
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    @Override
    public UsersPOJO getUserByEmail(String email) {
        return userMapper.toPOJO(usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
    }
}
