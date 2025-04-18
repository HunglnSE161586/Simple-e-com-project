package com.hung.shop.user.internal;

import com.hung.shop.share.UsersPOJO;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    public UserDto registerUser(UserCreateRequest userCreateRequest);
    public UserDto getUserById(Long id);
    public List<UserDto> getAllUsers();
    public Page<UserDto> getPagedUsers(Integer page, Integer size);
    public UserDto updateUser(Long id, UserUpdateRequest userUpdateRequest);
    public UserDto updateUserStatus(Long id, Boolean isActive);
    UsersPOJO getUserByEmail(String email);
}
