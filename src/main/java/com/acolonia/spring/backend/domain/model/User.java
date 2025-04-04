package com.acolonia.spring.backend.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUser;

    @NotNull
    @Column(name = "nombre")
    @Size(max = 20)
    private String nameUser;

    @NotNull
    @Column(name = "apellido")
    @Size(max = 20)
    private String userLastname;

    @NotNull
    @Size(max = 15)
    @Column(unique = true)
    private String username;

    @Email
    @NotNull
    @Size(max = 50)
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    //relacion entre base de datos
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Role> userRoles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_departamento", referencedColumnName = "id_departamento")
    private Department department;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList;

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nameUser='" + nameUser + '\'' +
                ", userLastname='" + userLastname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRoles=" + userRoles +
                ", department=" + department+
                '}';
    }
}
