package com.pacifique.dining.authService.repository;

import com.pacifique.dining.authService.entity.Role;
import com.pacifique.dining.authService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User>  findByEmail(String email);

    List<User> findAllByRole(Role role);
}