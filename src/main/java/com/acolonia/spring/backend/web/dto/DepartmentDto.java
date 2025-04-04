package com.acolonia.spring.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {

    private Long idDepartment;
    private String departmentName;
    private Double maxAmount;
    private Double accumulatedAmount;

}
