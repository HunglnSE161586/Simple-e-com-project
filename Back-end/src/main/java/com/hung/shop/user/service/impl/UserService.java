package com.hung.shop.user.service.impl;

import com.hung.shop.auth.service.IUserAuthService;
import com.hung.shop.user.entity.User;
import com.hung.shop.user.exception.DefaultRoleNotFoundException;
import com.hung.shop.user.exception.EmailExistedException;
import com.hung.shop.user.exception.UserNotFoundException;
import com.hung.shop.userRole.service.IUserRoleService;
import com.hung.shop.share.UserRolePOJO;
import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import com.hung.shop.user.service.IUserService;
import com.hung.shop.user.mapper.UserMapper;
import com.hung.shop.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final String DEFAULT_ROLE = "ROLE_CUSTOMER";
    private final UsersRepository usersRepository;
    private final IUserRoleService userRoleService;
    private final IUserAuthService userAuthService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registerUser(UserCreateRequest userCreateRequest) {
        if (usersRepository.findByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new EmailExistedException("Email already exists: " + userCreateRequest.getEmail());
        }
        UserRolePOJO defaultRole = userRoleService.findByRoleName(DEFAULT_ROLE)
                .orElseThrow(() -> new DefaultRoleNotFoundException("Default Role not found, please check if the role exists in the database: " + DEFAULT_ROLE + " or create a new one."));

        User user = userMapper.userCreateRequestToEntity(userCreateRequest);
        user.setRoleId(defaultRole.getRoleId());

        user = usersRepository.save(user);

        String password = passwordEncoder.encode(userCreateRequest.getPassword());
        userAuthService.saveUserAuth(user.getId(), password);
        return userMapper.toDto(user);
    }

    public Optional<UserDto> getUserById(Long id) {
        return usersRepository.findById(id)
                .map(userMapper::toDto)
                .or(() -> {
                    throw new UserNotFoundException("User not found");
                });
    }

    public List<UserDto> getAllUsers() {
        return usersRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public Page<UserDto> getPagedUsers(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return usersRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Transactional
    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        Optional<User> userOptional = usersRepository.findById(id).or(() -> {
            throw new UserNotFoundException("User not found");
        });
        return userMapper.toDto(usersRepository.save(
                userMapper.userUpdateRequestToEntity(userUpdateRequest, userOptional.get())
        ));
    }

    @Transactional
    public UserDto updateUserStatus(Long id, Boolean isActive) {
        Optional<User> userOptional = usersRepository.findById(id).or(() -> {
            throw new UserNotFoundException("User not found");
        });
        User user = userOptional.get();
        user.setIsActive(isActive);
        return userMapper.toDto(usersRepository.save(user));
    }

    @Override
    public UsersPOJO getUserByEmail(String email) {
        return userMapper.toPOJO(usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email)));
    }
}