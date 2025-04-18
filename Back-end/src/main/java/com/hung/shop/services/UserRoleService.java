package com.hung.shop.services;

import com.hung.shop.mapper.RoleMapper;
import com.hung.shop.repositories.UserRoleRepository;
import com.hung.shop.share.UserRolePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService implements IUserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleMapper roleMapper;
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
