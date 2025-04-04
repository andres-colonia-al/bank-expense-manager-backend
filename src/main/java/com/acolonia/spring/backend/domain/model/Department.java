package com.acolonia.spring.backend.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "departamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Long idDepartment;

    @NotNull
    @Column(name = "nombre_departamento")
    @Size(max = 20)
    private String departmentName;

    @NotNull
    @Column(name = "monto_maximo", columnDefinition = "DECIMAL(10,2)")
    private Double maxAmount;

    @Column(name = "monto_acumulado", columnDefinition = "DECIMAL(10,2)")
    private Double accumulatedAmount;

    @Override
    public String toString() {
        return "Department{" +
                "idDepartment=" + idDepartment +
                ", departmentName='" + departmentName + '\'' +
                ", maxAmount=" + maxAmount +
                ", accumulatedAmount=" + accumulatedAmount +
                '}';
    }
}
