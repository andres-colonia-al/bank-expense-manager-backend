package com.acolonia.spring.backend.application.mappers;

import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepartmentMapper {

    public Department toDepartment (DepartmentDto departmentDto) {
        log.info("[INFO] Mapeando DepartmentDto a Department");

        if (departmentDto == null) {
            log.error("[ERROR] en departmentDto los valores no puede ser nulos");
            return null;
        }

        Department department = Department.builder()
                .idDepartment(departmentDto.getIdDepartment())
                .departmentName(departmentDto.getDepartmentName())
                .maxAmount(departmentDto.getMaxAmount())
                .build();

        log.info("[INFO] Mapeo de Department realizado correctamente: {}", department.toString());
        return department;
    }

    public DepartmentDto toDepartmentDto (Department department) {
        log.info("[INFO] Mapeando Department a DepartmentDto");

        if (department == null) {
            log.error("[ERROR] en department los valores no puede ser nulos");
            return null;
        }

        DepartmentDto departmentDto = DepartmentDto.builder()
                .idDepartment(department.getIdDepartment())
                .departmentName(department.getDepartmentName())
                .maxAmount(department.getMaxAmount())
                .accumulatedAmount(department.getAccumulatedAmount())
                .build();

        log.info("[INFO] Mapeo de DepartmentDto realizado correctamente: {}", departmentDto.toString());
        return departmentDto;
    }

}
