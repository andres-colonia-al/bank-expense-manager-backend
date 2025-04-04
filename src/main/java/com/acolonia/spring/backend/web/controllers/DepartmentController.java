package com.acolonia.spring.backend.web.controllers;

import com.acolonia.spring.backend.application.services.DepartmentService;
import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/department")
@CrossOrigin(origins = {"http://localhost:8081","http://192.168.1.100:8082"})
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartment() {
        log.info("[INFO] Ejecutando el controlador getAllDepartment");
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{idDepartment}")
    public ResponseEntity<Optional<DepartmentDto>> getDepartmentById (@PathVariable  Long idDepartment){
        log.info("[INFO] Ejecutando el controlador getDepartmentById con el id: {}", idDepartment);
        Optional<DepartmentDto> departmentDtoOptional = departmentService.getDepartmentById(idDepartment);
       if (departmentDtoOptional.isPresent()){
           log.info("[INFO] Se encontro el departamento con el id: {}", idDepartment);
           return new ResponseEntity<>(departmentDtoOptional, HttpStatus.OK);
       } else {
           log.error("[ERROR] No se encontro el departamento con el id: {}", idDepartment);
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment (@RequestBody DepartmentDto newDepartmentDto){
        log.info("[INFO] Ejecutando el controlador saveDepartment");
        DepartmentDto departmentDto = departmentService.saveDepartment(newDepartmentDto);
        if (departmentDto == null) {
            log.error("[ERROR] No se ejecuto el saveDepartment correctamente");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("[INFO] Se creo el departamento con exito: {}", departmentDto);
            return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{idDepartment}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto newDepartmentDto,
                                                          @PathVariable Long idDepartment){
        log.info("[INFO] Ejecutando el controlador updateDepartment");
        DepartmentDto departmentDto = departmentService.updateDepartment(newDepartmentDto, idDepartment);
        if (departmentDto == null) {
            log.error("[ERROR] No se ejecuto el updateDepartment correctamente");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("[INFO] Se Actualizo el departamento con exito: {}", departmentDto);
            return new ResponseEntity<>(departmentDto, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idDepartment}")
    public ResponseEntity<Void> deleteDepartmentById (@PathVariable Long idDepartment){
        log.info("[INFO] Ejecutando el controlador deleteDepartmentById");
        boolean isDeleted = departmentService.deleteDepartmentById(idDepartment);
        if (isDeleted){
            log.info("[INFO] Se creo elimino con exito el departamento con id: {}", idDepartment);
            return new ResponseEntity<>(HttpStatus.OK);
        } {
            log.error("[ERROR] No se ejecuto el deleteDepartmentById correctamente");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
