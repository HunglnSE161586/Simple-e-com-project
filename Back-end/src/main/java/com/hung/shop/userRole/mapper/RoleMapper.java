package com.hung.shop.userRole.mapper;

import com.hung.shop.userRole.entity.UserRole;
import com.hung.shop.share.UserRolePOJO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    UserRolePOJO toPOJO(UserRole userRole);
}
