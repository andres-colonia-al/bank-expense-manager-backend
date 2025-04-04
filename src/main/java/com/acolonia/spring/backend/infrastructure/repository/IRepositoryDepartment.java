package com.acolonia.spring.backend.infrastructure.repository;

import com.acolonia.spring.backend.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryDepartment extends JpaRepository<Department,Long> {
}
