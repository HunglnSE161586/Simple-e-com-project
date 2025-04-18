package com.hung.shop.mapper;

import com.hung.shop.entity.UserRoles;
import com.hung.shop.share.UserRolePOJO;
import org.apache.catalina.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    UserRolePOJO toPOJO(UserRoles userRoles);
    UserRoles toEntity(UserRolePOJO userRolePOJO);
}
