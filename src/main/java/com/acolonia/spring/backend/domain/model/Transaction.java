package com.acolonia.spring.backend.domain.model;

import com.acolonia.spring.backend.domain.enums.RoleCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaction;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre_transaccion")
    private String nameTransaction;

    @NotNull
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descriptionTransaction;

    @NotNull
    @Column(name = "monto", columnDefinition = "DECIMAL(10,2)")
    private Double amountTransaction;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleCategory category;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private User user;

    @Override
    public String toString() {
        return "Transaction{" +
                "idTransaction=" + idTransaction +
                ", nameTransaction='" + nameTransaction + '\'' +
                ", descriptionTransaction='" + descriptionTransaction + '\'' +
                ", amountTransaction=" + amountTransaction +
                ", createdAt=" + createdAt +
                ", category=" + category +
                ", user=" + user.getIdUser() +
                '}';
    }
}
