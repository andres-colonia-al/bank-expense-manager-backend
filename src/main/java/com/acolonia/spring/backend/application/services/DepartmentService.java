package com.acolonia.spring.backend.application.services;

import com.acolonia.spring.backend.application.mappers.DepartmentMapper;
import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.infrastructure.repository.IRepositoryDepartment;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {

    private final IRepositoryDepartment departmentIRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(IRepositoryDepartment departmentIRepository, DepartmentMapper departmentMapper) {
        this.departmentIRepository = departmentIRepository;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> getAllDepartments () {
        log.info("[INFO] iniciando el servicio getAllDepartments");
        List<Department> departmentList = departmentIRepository.findAll();

        List<DepartmentDto> departmentDtoList = departmentList.stream()
                .map(departmentMapper::toDepartmentDto)
                .toList();

        log.info("[INFO] finalizando con exito el servicio getAllDepartments: {}", departmentDtoList.size());
        return departmentDtoList;
    }

    public Optional<DepartmentDto> getDepartmentById (Long idDepartment) {
        log.info("[INFO] iniciando el servicio getDepartmentById");
        Optional<Department> optionalDepartment = departmentIRepository.findById(idDepartment);

        if (optionalDepartment.isEmpty()) {
            log.info("[INFO] optionalDepartment - Optional despues de persistencia vacio");
            return Optional.empty();
        }

        Optional<DepartmentDto> departmentDtoOptional = optionalDepartment
                .map(departmentMapper::toDepartmentDto);

        log.info("[INFO] Finalizando con exito  el servicio getDepartmentById: {}", departmentDtoOptional);
        return departmentDtoOptional;
    }

    @Transactional
    public DepartmentDto saveDepartment (DepartmentDto newDepartmentDto) {

        if (newDepartmentDto.getIdDepartment() == null){
            log.info("[INFO] iniciando el servicio saveDepartment: {},", newDepartmentDto.getMaxAmount());

            Department newDepartment = departmentIRepository.save(departmentMapper.toDepartment(newDepartmentDto));
            DepartmentDto DepartmentDto = departmentMapper.toDepartmentDto(newDepartment);

            log.info("[INFO] Finalizando con exito  el servicio saveDepartment: {}", newDepartment);
            return DepartmentDto;
        } else {
            log.error("[ERROR] el id del newDepartmentDto debe de ser nulo!: {}", newDepartmentDto.getIdDepartment());
            throw new IllegalArgumentException("El ID debe ser nulo al crear un nuevo departamento.");
        }
    }

    @Transactional
    public DepartmentDto updateDepartment (DepartmentDto newDepartmentDto, Long idDepartment) {
        log.info("[INFO] iniciando el servicio updateDepartment");
        Department department = departmentIRepository.findById(idDepartment)
                .orElseThrow(()-> new RuntimeException("Departamento no encontrado con id " + idDepartment));

        if (!(newDepartmentDto.getDepartmentName() == null)){
            department.setDepartmentName(newDepartmentDto.getDepartmentName());
        }

        if (!(newDepartmentDto.getMaxAmount() == null)){
            department.setMaxAmount(newDepartmentDto.getMaxAmount());
        }

        DepartmentDto departmentDtoUpdate = departmentMapper
                .toDepartmentDto(departmentIRepository.save(department));

        log.info("[INFO] Finalizando con exito  el servicio updateDepartment: {}", departmentDtoUpdate);
        return departmentDtoUpdate;
    }

    @Transactional
    public Boolean deleteDepartmentById (Long idDepartment) {
        log.info("[INFO] iniciando el servicio deleteDepartmentById");
        boolean department = departmentIRepository.existsById(idDepartment);

        if (!department) {
            log.error("[ERROR] el id del departamento no se encontro en la base de datos!: {}", idDepartment);
            return false;
        } else {
            departmentIRepository.deleteById(idDepartment);
            log.info("[INFO] Finalizando con exito  el servicio deleteDepartmentById: {}", idDepartment);
            return true;
        }
    }
}
