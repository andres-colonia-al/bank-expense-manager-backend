package com.acolonia.spring.backend.application.mappers;

import com.acolonia.spring.backend.domain.enums.RoleName;
import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.domain.model.Role;
import com.acolonia.spring.backend.domain.model.User;
import com.acolonia.spring.backend.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserMapper {

    public User toUser (UserDto userDto, Department department){
        log.info("[INFO] Mapeando UserCreateDto a User");

        if (userDto == null || department == null) {
            log.error("[ERROR] en toUser los valores no puede ser nulos");
            return null;
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            log.error("[ERROR] La contraseña no puede ser nula o vacía");
            return null;
        }

        User user = User.builder()
                .idUser(userDto.getIdUser())
                .nameUser(userDto.getNameUser())
                .userLastname(userDto.getUserLastname())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .userRoles(mapSetRole(userDto.getRoles()))
                .department(department)
                .build();

        log.info("[INFO] Mapeo de User realizado correctamente: {}", user);
        return user;
    }

    public UserDto toUserDto (User user){
        log.info("[INFO] Mapeando user a UserDto");

        if (user == null) {
            log.error("[ERROR] en user los valores no puede ser nulos");
            return null;
        }

        UserDto userDto = UserDto.builder()
                .idUser(user.getIdUser())
                .nameUser(user.getNameUser())
                .userLastname(user.getUserLastname())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(mapSetString(user.getUserRoles()))
                .idDepartment(user.getDepartment().getIdDepartment())
                .build();

        log.info("[INFO] Mapeo de UserDto realizado correctamente: {}", userDto);
        return userDto;
    }

    public static Set<Role> mapSetRole (Set<String> roles) {
        try {
            Set<Role> roleSet = roles.stream()
                    .map(role -> Role.builder()
                            .roleName(RoleName.valueOf(role))
                            .build())
                    .collect(Collectors.toSet());
            log.info("[INFO] Role mapeado correctamente: {}:", roleSet);
            return roleSet;
        } catch (IllegalArgumentException e) {
            log.error("[ERROR] Error al convertir el role: '{}'. role inválida.", roles, e);
            throw new IllegalArgumentException ("roles invalidos " + roles);
        }
    }

    public static Set<String> mapSetString (Set<Role> roleSet){
        try{
            Set<String> roleString = roleSet.stream()
                    .map(role -> role.getRoleName().name())
                    .collect(Collectors.toSet());
            log.info("[INFO] String mapeado correctamente: {}:", roleString);
            return roleString;
        } catch (IllegalArgumentException e) {
            log.error("[ERROR] Error al convertir el roleSet: '{}'. role inválida.", roleSet, e);
            throw new IllegalArgumentException ("roles invalidos " + roleSet);
        }
    }
}
