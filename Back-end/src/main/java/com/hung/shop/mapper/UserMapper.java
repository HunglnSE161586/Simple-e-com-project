package com.hung.shop.mapper;

import com.hung.shop.dto.request.UserCreateDto;
import com.hung.shop.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(UserCreateDto userCreateDto);
}
