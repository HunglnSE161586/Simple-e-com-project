package com.hung.shop.repositories;

import com.hung.shop.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
    Optional<UserRoles> findByRoleName(String roleName);
}
