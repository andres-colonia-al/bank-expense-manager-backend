package com.acolonia.spring.backend.application.services;

import com.acolonia.spring.backend.application.mappers.DepartmentMapper;
import com.acolonia.spring.backend.application.mappers.TransactionMapper;
import com.acolonia.spring.backend.application.mappers.UserMapper;
import com.acolonia.spring.backend.domain.enums.RoleCategory;
import com.acolonia.spring.backend.domain.model.Department;
import com.acolonia.spring.backend.domain.model.Transaction;
import com.acolonia.spring.backend.domain.model.User;
import com.acolonia.spring.backend.infrastructure.repository.IRepositoryTransaction;
import com.acolonia.spring.backend.web.dto.DepartmentDto;
import com.acolonia.spring.backend.web.dto.TransactionDto;
import com.acolonia.spring.backend.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {

    private final IRepositoryTransaction transactionIRepository;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final TransactionMapper transactionMapper;
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public TransactionService(IRepositoryTransaction transactionIRepository, UserService userService, DepartmentService departmentService, TransactionMapper transactionMapper, UserMapper userMapper, DepartmentMapper departmentMapper) {
        this.transactionIRepository = transactionIRepository;
        this.userService = userService;
        this.departmentService = departmentService;
        this.transactionMapper = transactionMapper;
        this.userMapper = userMapper;
        this.departmentMapper = departmentMapper;
    }

    public List<TransactionDto> getAllTransactions () {
        log.info("[INFO] iniciando el servicio getAllTransactions");
        List<Transaction> transactionList = transactionIRepository.findAll();

        final var transactionDtoList = getTransactionDtos(transactionList);

        log.info("[INFO] finalizando con exito el servicio getAllTransactions: {}", transactionDtoList.size());
        return transactionDtoList;
    }

    public Optional<TransactionDto> getTransactionById (Long idTransaction) {
        log.info("[INFO] iniciando el servicio getTransactionById");
        Optional<Transaction> optionalTransaction = transactionIRepository.findById(idTransaction);

        if (optionalTransaction.isEmpty()) {
            log.info("[INFO] optionalTransaction - Optional despues de persistencia vacio");
            return Optional.empty();
        }

        Optional<TransactionDto> transactionDtoOptional = optionalTransaction
                .map(transactionMapper::toTransactionDto);

        log.info("[INFO] Finalizando con exito  el servicio getAllTransactions: {}", transactionDtoOptional);
        return transactionDtoOptional;
    }

    public List<TransactionDto> findByUserIdUser (Long userId) {
        log.info("[INFO] iniciando el servicio findByUserIdUser");
        List<Transaction> transactionList = transactionIRepository.findByUserIdUserOrderByCreatedAtDesc(userId);

        final var transactionDtoList = getTransactionDtos(transactionList);

        log.info("[INFO] finalizando con exito el servicio findByUserIdUser: {}", transactionDtoList.size());
        return transactionDtoList;
    }

    public List<TransactionDto> findByCategory (RoleCategory category) {
        log.info("[INFO] iniciando el servicio findByCategory");
        List<Transaction> transactionList = transactionIRepository.findByCategory(category);

        final var transactionDtoList = getTransactionDtos(transactionList);

        log.info("[INFO] finalizando con exito el servicio findByCategory: {}", transactionDtoList.size());
        return transactionDtoList;
    }

    public List<TransactionDto> findByDepartmentId (Long deptId) {
        log.info("[INFO] iniciando el servicio findByDepartmentId");
        List<Transaction> transactionList = transactionIRepository.findByDepartmentId(deptId);

        final var transactionDtoList = getTransactionDtos(transactionList);

        log.info("[INFO] finalizando con exito el servicio findByDepartmentId: {}", transactionDtoList.size());
        return transactionDtoList;
    }

    @Transactional
    public TransactionDto saveTransaction (TransactionDto newTransactionDto) {

        if (newTransactionDto.getIdTransaction() == null){
            log.info("[INFO] iniciando el servicio saveTransaction");
            newTransactionDto.setDateTime(LocalDateTime.now());

            UserDto userDto = userService.getUserById(newTransactionDto.getIdUser())
                    .orElseThrow(()-> new RuntimeException("Usuario no encontrado con id " + newTransactionDto.getIdTransaction()));

            DepartmentDto departmentDto = departmentService.getDepartmentById(userDto.getIdDepartment())
                    .orElseThrow(()-> new RuntimeException("Departamento no encontrado con id " + userDto.getIdDepartment()));

            Department department = departmentMapper.toDepartment(departmentDto);
            User user = userMapper.toUser(userDto, department);

            Transaction newTransaction = transactionIRepository.save(transactionMapper.toTransaction(newTransactionDto,user));
            TransactionDto transactionDto = transactionMapper.toTransactionDto(newTransaction);

            log.info("[INFO] Finalizando con exito  el servicio saveTransaction: {}", newTransaction);
            return transactionDto;
        } else {
            log.error("[ERROR] el id del newTransactionDto debe de ser nulo!: {}", newTransactionDto.getIdTransaction());
            throw new IllegalArgumentException("El ID debe ser nulo al crear una nueva transacción.");
        }
    }

    @Transactional
    public Boolean deleteTransactionById (Long idTransaction) {
        log.info("[INFO] iniciando el servicio deleteTransactionById");
        boolean transaction = transactionIRepository.existsById(idTransaction);

        if (!transaction) {
            log.error("[ERROR] el id del usuario no se encontro en la base de datos!: {}", idTransaction);
            return false;
        } else {
            transactionIRepository.deleteById(idTransaction);
            log.info("[INFO] Finalizando con exito  el servicio deleteTransactionById: {}", idTransaction);
            return true;
        }
    }

    private List<TransactionDto> getTransactionDtos(List<Transaction> transactionList) {
        return transactionList.stream()
                .map(transactionMapper::toTransactionDto)
                .toList();
    }
}
