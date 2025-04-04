package com.acolonia.spring.backend.application.services;

import com.acolonia.spring.backend.application.mappers.DepartmentMapper;
import com.acolonia.spring.backend.application.mappers.UserMapper;
import com.acolonia.spring.backend.domain.enums.RoleName;
import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.domain.model.Role;
import com.acolonia.spring.backend.domain.model.User;
import com.acolonia.spring.backend.infrastructure.repository.IRepositoryUser;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import com.acolonia.spring.backend.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final IRepositoryUser userIRepository;
    private final DepartmentService departmentService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public UserService(IRepositoryUser userIRepository, DepartmentService departmentService, RoleService roleService, UserMapper userMapper, DepartmentMapper departmentMapper) {
        this.userIRepository = userIRepository;
        this.departmentService = departmentService;
        this.roleService = roleService;
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;
    }

    public List<UserDto> getAllUsers () {
        log.info("[INFO] iniciando el servicio getAllUsers");
        List<User> userList = userIRepository.findAll();

        final var userDtoList = getUserDtos(userList);

        log.info("[INFO] finalizando con exito el servicio getAllUsers: {}", userDtoList.size());
        return userDtoList;
    }

    public Optional<UserDto> getUserById (Long idUser) {
        log.info("[INFO] iniciando el servicio getUserById");
        Optional<User> optionalUser = userIRepository.findById(idUser);

        if (optionalUser.isEmpty()) {
            log.info("[INFO] optionalUser - Optional despues de persistencia vacio");
            return Optional.empty();
        }

        Optional<UserDto> userDtoOptional = optionalUser
                .map(userMapper::toUserDto);

        log.info("[INFO] Finalizando con exito  el servicio getUserById: {}", userDtoOptional);
        return userDtoOptional;
    }

    public Optional<UserDto> authUser (UserDto userDto){
        log.info("[INFO] Iniciando el servicio authUser para el usuario: {}", userDto.getUsername());

        Optional<User> usernameAndPassword = userIRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());

        if (usernameAndPassword.isPresent()){
            Optional<UserDto> newUserDto = usernameAndPassword
                    .map(userMapper::toUserDto);
            log.info("[INFO] se autentico correctamente el usuario {}", usernameAndPassword.get().getUsername());
            return newUserDto;
        } else
            log.info("[INFO] Iniciando el servicio authUser para el usuario: {}", userDto.getUsername());
        return Optional.empty();
    }

    public List<UserDto> findByDepartmentAndIdDepartment (Long idDepartment) {
        log.info("[INFO] iniciando el servicio findByDepartmentAndIdDepartment");
        List<User> userList = userIRepository.findByDepartment_IdDepartment(idDepartment);

        final var userDtoList = getUserDtos(userList);

        log.info("[INFO] finalizando con exito el servicio getAllUsers: {}", userDtoList.size());
        return userDtoList;
    }

    @Transactional
    public UserDto saveUser (UserDto newUserDto) {

        if (newUserDto.getIdUser() == null){
            log.info("[INFO] iniciando el servicio saveUser");

            DepartmentDto departmentDto = departmentService.getDepartmentById(newUserDto.getIdDepartment())
                    .orElseThrow(()-> new RuntimeException("Departamento no encontrado con id " + newUserDto.getIdDepartment()));

            Set<Role> roles = newUserDto.getRoles().stream()
                    .map(role -> roleService.findByRoleName(RoleName.valueOf(role))
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + role)))
                    .collect(Collectors.toSet());

            User newUser = userMapper.toUser(newUserDto, departmentMapper.toDepartment(departmentDto));
            newUser.setUserRoles(roles);
            userIRepository.save(newUser);

            log.info("[INFO] ANTES {}", newUser);

            UserDto userDto = userMapper.toUserDto(newUser);
            log.info("[INFO] despues {}", userDto);


            log.info("[INFO] Finalizando con exito  el servicio saveUser: {}", userDto);
            return userDto;
        } else {
            log.error("[ERROR] el id del newUserDto debe de ser nulo!: {}", newUserDto.getIdUser());
            throw new IllegalArgumentException("El ID debe ser nulo al crear un nuevo usuario.");
        }
    }

    @Transactional
    public UserDto updateUser (UserDto newUserDto, Long idUser) {
        log.info("[INFO] iniciando el servicio updateUser");
        User user = userIRepository.findById(idUser)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado con id " + idUser));

        if (!(newUserDto.getNameUser() == null)){
            user.setNameUser(newUserDto.getNameUser());
        }

        if (!(newUserDto.getUserLastname() == null)){
            user.setUserLastname(newUserDto.getUserLastname());
        }

        if (!(newUserDto.getUsername() == null)){
            user.setUsername(newUserDto.getUsername());
        }

        if (!(newUserDto.getEmail() == null)){
            user.setEmail(newUserDto.getEmail());
        }

        if (!(newUserDto.getPassword() == null)){
            user.setPassword(newUserDto.getPassword());
        }

        if (!(newUserDto.getIdDepartment() == null )){
            Department department = departmentService.getDepartmentById(newUserDto.getIdDepartment())
                            .map(departmentMapper::toDepartment)
                                    .orElseThrow(() ->
                                            new RuntimeException("Departamento no encontrado con id " + newUserDto.getIdDepartment()));

            user.setDepartment(department);
        }


        if (!(newUserDto.getRoles().isEmpty())) {

            Set<Role> roles = newUserDto.getRoles().stream()
                    .map(role -> roleService.findByRoleName(RoleName.valueOf(role))
                            .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + role)))
                    .collect(Collectors.toSet());

            user.setUserRoles(roles);
        }

        UserDto userDtoUpdate = userMapper
                .toUserDto(userIRepository.save(user));

        log.info("[INFO] Finalizando con exito  el servicio updateUser: {}", userDtoUpdate);
        return userDtoUpdate;
    }

    @Transactional
    public Boolean deleteUserById (Long idUser) {
        log.info("[INFO] iniciando el servicio deleteUserById");
        boolean user = userIRepository.existsById(idUser);

        if (!user) {
            log.error("[ERROR] el id del usuario no se encontro en la base de datos!: {}", idUser);
            return false;
        } else {
            userIRepository.deleteById(idUser);
            log.info("[INFO] Finalizando con exito  el servicio deleteUserById: {}", idUser);
            return true;
        }
    }

    private List<UserDto> getUserDtos(List<User> userList) {
        return userList.stream()
                .map(userMapper::toUserDto)
                .toList();
    }


}
