package com.hung.shop.user.mapper;

import com.hung.shop.share.UsersPOJO;
import com.hung.shop.auth.mapper.UserAuthMapper;
import com.hung.shop.user.dto.request.UserCreateRequest;
import com.hung.shop.user.dto.request.UserUpdateRequest;
import com.hung.shop.user.dto.response.UserDto;
import com.hung.shop.user.entity.Users;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = UserAuthMapper.class)
public interface UserMapper {
    Users userCreateRequestToEntity(UserCreateRequest userCreateRequest);
    @Mapping(target = "userId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users userUpdateRequestToEntity(UserUpdateRequest userUpdateRequest, @MappingTarget Users entity);
    UserDto toDto(Users entity);
    @Mapping(target = "userRolePOJO.roleId", source = "roleId")
    UsersPOJO toPOJO(Users entity);
    //@Mapping(target = "userId", source = "userId")
    @Mapping(target = "roleId", source = "userRolePOJO.roleId")
    Users toEntity(UsersPOJO usersPOJO);
}
