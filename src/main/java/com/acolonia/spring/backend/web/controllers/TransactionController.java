package com.acolonia.spring.backend.web.controllers;

import com.acolonia.spring.backend.application.services.TransactionService;
import com.acolonia.spring.backend.domain.enums.RoleCategory;
import com.acolonia.spring.backend.web.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/transaction")
@CrossOrigin(origins = "${app.cors.allowed-origins}")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions (){
        log.info("[INFO] Ejecutando el controlador getAllDepartment");
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    //React
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<TransactionDto>> findTransactionByUserIdUser (@PathVariable Long userId){
        log.info("[INFO] Ejecutando el controlador findTransactionByUserIdUser");
        return new ResponseEntity<>(transactionService.findByUserIdUser(userId), HttpStatus.OK);
    }

    @GetMapping("/category/{roleCategory}")
    public ResponseEntity<List<TransactionDto>> findTransactionByCategory (@PathVariable RoleCategory roleCategory){
        log.info("[INFO] Ejecutando el controlador findTransactionByCategory");
        return new ResponseEntity<>(transactionService.findByCategory(roleCategory), HttpStatus.OK);
    }

    @GetMapping("/departments/{deptId}")
    public ResponseEntity<List<TransactionDto>> findTransactionByDepartmentId (@PathVariable Long deptId){
        log.info("[INFO] Ejecutando el controlador findTransactionByDepartmentId");
        return new ResponseEntity<>(transactionService.findByDepartmentId(deptId), HttpStatus.OK);
    }

    @GetMapping("/{idTransaction}")
    public ResponseEntity<Optional<TransactionDto>> getTransactionById (@PathVariable Long idTransaction){
        log.info("[INFO] Ejecutando el controlador getTransactionById con el id: {}", idTransaction);
        Optional<TransactionDto> transactionDtoOptional = transactionService.getTransactionById(idTransaction);
        if (transactionDtoOptional.isPresent()){
            log.info("[INFO] Se encontro la transaccion con el id: {}", idTransaction);
            return new ResponseEntity<>(transactionDtoOptional, HttpStatus.OK);
        } else {
            log.error("[ERROR] No se encontro la transaccion con el id: {}", idTransaction);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TransactionDto> saveTransaction (@RequestBody TransactionDto newTransactionDto){
        log.info("[INFO] Ejecutando el controlador saveTransaction");
        TransactionDto transactionDto = transactionService.saveTransaction(newTransactionDto);
        if (transactionDto == null) {
            log.error("[ERROR] No se ejecuto el saveTransaction correctamente");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            log.info("[INFO] Se creo la transacción con exito: {}", transactionDto);
            return new ResponseEntity<>(transactionDto, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{idTransaction}")
    public ResponseEntity<Void> deleteTransactionById (@PathVariable Long idTransaction){
        log.info("[INFO] Ejecutando el controlador deleteTransactionById");
        boolean isDeleted = transactionService.deleteTransactionById(idTransaction);
        if (isDeleted){
            log.info("[INFO] Se elimino con exito la trasancción con id: {}", idTransaction);
            return new ResponseEntity<>(HttpStatus.OK);
        } {
            log.error("[ERROR] No se ejecuto el deleteTransactionById correctamente");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
