package com.hung.shop.userRole.service.impl;

import com.hung.shop.userRole.mapper.RoleMapper;
import com.hung.shop.userRole.repository.UserRoleRepository;
import com.hung.shop.share.UserRolePOJO;
import com.hung.shop.userRole.service.IUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService implements IUserRoleService {
    private final UserRoleRepository userRoleRepository;

    private final RoleMapper roleMapper;
    public Optional<UserRolePOJO> findByRoleName(String roleName) {
        return userRoleRepository.findByRoleName(roleName)
                .map(roleMapper::toPOJO);
    }

    @Override
    public Optional<UserRolePOJO> findById(Long id) {
        return userRoleRepository.findById(id)
                .map(roleMapper::toPOJO);
    }
}
