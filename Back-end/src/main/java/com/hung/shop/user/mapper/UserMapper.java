package com.hung.shop.user.mapper;

import com.hung.shop.share.UsersPOJO;
import com.hung.shop.auth.mapper.UserAuthMapper;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import com.hung.shop.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = UserAuthMapper.class)
public interface UserMapper {
    User userCreateRequestToEntity(UserCreateRequest userCreateRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User userUpdateRequestToEntity(UserUpdateRequest userUpdateRequest, @MappingTarget User entity);
    UserDto toDto(User entity);
    @Mapping(target = "userRolePOJO.id", source = "roleId")
    UsersPOJO toPOJO(User entity);
    //@Mapping(target = "userId", source = "userId")
    @Mapping(target = "roleId", source = "userRolePOJO.id")
    User toEntity(UsersPOJO usersPOJO);
}
