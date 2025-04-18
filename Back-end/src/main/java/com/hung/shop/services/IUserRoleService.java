package com.hung.shop.services;

import com.hung.shop.share.UserRolePOJO;

import java.util.Optional;

public interface IUserRoleService {
    Optional<UserRolePOJO> findByRoleName(String roleName);
    Optional<UserRolePOJO> findById(Long id);
}
