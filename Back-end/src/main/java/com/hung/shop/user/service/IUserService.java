package com.hung.shop.user.service;

import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDto registerUser(UserCreateRequest userCreateRequest);
    Optional<UserDto> getUserById(Long id);
    List<UserDto> getAllUsers();
    Page<UserDto> getPagedUsers(Integer page, Integer size);
    UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest);
    UserDto updateUserStatus(Long id, Boolean isActive);
    UsersPOJO getUserByEmail(String email);
}
