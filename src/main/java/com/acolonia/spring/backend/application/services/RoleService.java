package com.acolonia.spring.backend.application.services;

import com.acolonia.spring.backend.domain.enums.RoleName;
import com.acolonia.spring.backend.domain.model.Role;
import com.acolonia.spring.backend.infrastructure.repository.IRepositoryRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    private final IRepositoryRole roleIRepository;

    public RoleService(IRepositoryRole roleIRepository) {
        this.roleIRepository = roleIRepository;
    }

    public Optional<Role> findByRoleName(RoleName roleName) {
        log.info("[INFO] iniciando el servicio findByRoleName");
        Optional<Role> optionalRole = roleIRepository.findByRoleName(roleName);
        if (optionalRole.isPresent()){
            log.info("[INFO] iniciando el servicio findByRoleName {}", optionalRole.get().getRoleName() );
            return optionalRole;
        } else {
            log.error("No se encontro el roleName en la base de datos {}", roleName);
            throw new IllegalArgumentException("No se encontro el roleName en la base de datos");
        }
    }
}
