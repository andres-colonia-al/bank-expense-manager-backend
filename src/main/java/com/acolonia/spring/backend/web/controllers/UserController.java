package com.acolonia.spring.backend.web.controllers;

import com.acolonia.spring.backend.application.services.UserService;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import com.acolonia.spring.backend.web.dto.TransactionDto;
import com.acolonia.spring.backend.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:8081","http://192.168.1.100:8082"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser (){
        log.info("[INFO] Ejecutando el controlador getAllUser");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Optional<UserDto>> getUserById (@PathVariable Long idUser){
        log.info("[INFO] Ejecutando el controlador getUserById con el id: {}", idUser);
        Optional<UserDto> userDtoOptional = userService.getUserById(idUser);
        if (userDtoOptional.isPresent()){
            log.info("[INFO] Se encontro el usuario con el id: {}", idUser);
            return new ResponseEntity<>(userDtoOptional, HttpStatus.OK);
        } else {
            log.error("[ERROR] No se encontro el usuario con el id: {}", idUser);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/department/{idDepartment}")
    public ResponseEntity<List<UserDto>> findUserByDepartmentAndIdDepartment (@PathVariable Long idDepartment){
        log.info("[INFO] Ejecutando el controlador findUserByDepartmentAndIdDepartment");
        return new ResponseEntity<>(userService.findByDepartmentAndIdDepartment(idDepartment), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser (@RequestBody UserDto newUserDto){
        log.info("[INFO] Ejecutando el controlador saveUser");
        UserDto userDto = userService.saveUser(newUserDto);
        if (userDto == null) {
            log.error("[ERROR] No se ejecuto el saveUser correctamente");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("[INFO] Se creo el usuario con exito: {}", userDto);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto newUserDto,
                                                          @PathVariable Long idUser){
        log.info("[INFO] Ejecutando el controlador updateUser");
        UserDto userDto = userService.updateUser(newUserDto, idUser);
        if (userDto == null) {
            log.error("[ERROR] No se ejecuto el updateUser correctamente");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("[INFO] Se Actualizo el usuario con exito: {}", userDto);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUserById (@PathVariable Long idUser){
        log.info("[INFO] Ejecutando el controlador deleteUserById");
        boolean isDeleted = userService.deleteUserById(idUser);
        if (isDeleted){
            log.info("[INFO] Se elimino con exito el usuario con id: {}", idUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } {
            log.error("[ERROR] No se ejecuto el deleteUserById correctamente");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto>  loginUser (@RequestBody UserDto userDto) {
        log.info("[INFO] Ejecutando el controlador loginUser");
        Optional<UserDto> isAuthenticated = userService.authUser(userDto);

        if (isAuthenticated.isPresent()) {
            log.info("[INFO] Se autenticó correctamente el usuario: {}", userDto.getUsername());
            UserDto newUserDto = UserDto.builder()
                    .idUser(isAuthenticated.get().getIdUser())
                    .nameUser(isAuthenticated.get().getNameUser())
                    .userLastname(isAuthenticated.get().getUserLastname())
                    .username(isAuthenticated.get().getUsername())
                    .roles(isAuthenticated.get().getRoles())
                    .email(isAuthenticated.get().getEmail())
                    .idDepartment(isAuthenticated.get().getIdDepartment())
                    .build();
            return new ResponseEntity<>(newUserDto, HttpStatus.OK);
        } else {
            log.error("[ERROR] No se ejecutó el loginUser correctamente");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
