package com.acolonia.spring.backend.domain.model;

import com.acolonia.spring.backend.domain.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRole;

    @NotNull
    @Column(name = "nombre_rol")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @NotNull
    @Column(name = "account_enable")
    private Boolean accountEnable;

    @NotNull
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @NotNull
    @Column(name = "credential_non_expired")
    private Boolean credentialNonExpired;

    @NotNull
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", roleName=" + roleName +
                ", accountEnable=" + accountEnable +
                ", accountNonExpired=" + accountNonExpired +
                ", credentialNonExpired=" + credentialNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                '}';
    }
}
