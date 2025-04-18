package com.hung.shop.userRole.mapper;

import com.hung.shop.userRole.entity.UserRoles;
import com.hung.shop.share.UserRolePOJO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    UserRolePOJO toPOJO(UserRoles userRoles);
    UserRoles toEntity(UserRolePOJO userRolePOJO);
}
