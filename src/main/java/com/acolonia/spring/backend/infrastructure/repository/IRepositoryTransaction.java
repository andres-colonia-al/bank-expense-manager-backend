package com.acolonia.spring.backend.infrastructure.repository;

import com.acolonia.spring.backend.domain.enums.RoleCategory;
import com.acolonia.spring.backend.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IRepositoryTransaction extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdUserOrderByCreatedAtDesc(Long userId);

    List<Transaction> findByCategory(RoleCategory category);

    @Query("""
        SELECT t FROM Transaction t
        JOIN t.user u
        JOIN u.department d
        WHERE d.idDepartment = :deptId
    """)
    List<Transaction> findByDepartmentId(Long deptId);

}
