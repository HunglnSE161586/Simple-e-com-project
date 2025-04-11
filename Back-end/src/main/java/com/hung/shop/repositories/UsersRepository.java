package com.hung.shop.repositories;

import com.hung.shop.entity.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = {"userAuths","role"})
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserId(Long userId);
}
