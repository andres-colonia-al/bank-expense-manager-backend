package com.acolonia.spring.backend.infrastructure.repository;

import com.acolonia.spring.backend.domain.enums.RoleName;
import com.acolonia.spring.backend.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryRole extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName (RoleName roleName);
}
