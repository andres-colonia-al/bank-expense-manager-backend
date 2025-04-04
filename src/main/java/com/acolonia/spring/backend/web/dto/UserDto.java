package com.acolonia.spring.backend.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long idUser;

    @Size(max = 20)
    private String nameUser;

    @Size(max = 20)
    private String userLastname;

    @Size(max = 15)
    private String username;

    @Email
    @Size(max = 50)
    private String email;

    private String password;
    private Set<String> roles = new HashSet<>();
    private Long idDepartment;

}

