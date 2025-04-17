package com.hung.shop.mapper;

import com.hung.shop.auth.mapper.UserAuthMapper;
import com.hung.shop.dto.request.UserCreateRequest;
import com.hung.shop.dto.request.UserUpdateRequest;
import com.hung.shop.dto.respond.UserDto;
import com.hung.shop.entity.Users;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = UserAuthMapper.class)
public interface UserMapper {
    Users userCreateRequestToEntity(UserCreateRequest userCreateRequest);
    @Mapping(target = "userId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users userUpdateRequestToEntity(UserUpdateRequest userUpdateRequest, @MappingTarget Users entity);
    UserDto toDto(Users entity);
}
