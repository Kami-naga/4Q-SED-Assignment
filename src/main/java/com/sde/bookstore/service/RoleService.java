package com.sde.bookstore.service;

import com.sde.bookstore.domain.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
    Optional<Role> findByRoleName(String roleName);
}
