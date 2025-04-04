package com.acolonia.spring.backend.infrastructure.repository;

import com.acolonia.spring.backend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepositoryUser extends JpaRepository<User, Long>{

    List<User> findByDepartment_IdDepartment (Long idDepartment);

    Optional<User> findByUsernameAndPassword (String username, String Password);

}
