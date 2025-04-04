package com.acolonia.spring.backend.application.mappers;

import com.acolonia.spring.backend.domain.enums.RoleCategory;
import com.acolonia.spring.backend.domain.model.Transaction;
import com.acolonia.spring.backend.domain.model.User;
import com.acolonia.spring.backend.web.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionMapper {

    public Transaction toTransaction (TransactionDto transactionDto, User user){
        log.info("[INFO] Mapeando TransactionCreateDto a Transaction");

        if (transactionDto == null || user == null) {
            log.error("[ERROR] en toTransaction los valores no puede ser nulos");
            return null;
        }

        Transaction transaction = Transaction.builder()
                .idTransaction(transactionDto.getIdTransaction())
                .nameTransaction(transactionDto.getNameTransaction())
                .descriptionTransaction(transactionDto.getDescriptionTransaction())
                .createdAt(transactionDto.getDateTime())
                .amountTransaction(transactionDto.getAmountTransaction())
                .category(mapCategory(transactionDto.getCategory()))
                .user(user)
                .build();

        log.info("[INFO] Mapeo de Transaction realizado correctamente: {}", transaction);
        return transaction;
    }

    public TransactionDto toTransactionDto (Transaction transaction) {
        log.info("[INFO] Mapeando transaction a TransactionDto");

        if (transaction == null) {
            log.error("[ERROR] en transaction los valores no puede ser nulos");
            return null;
        }

        TransactionDto transactionDto = TransactionDto.builder()
                .idTransaction(transaction.getIdTransaction())
                .nameTransaction(transaction.getNameTransaction())
                .descriptionTransaction(transaction.getDescriptionTransaction())
                .amountTransaction(transaction.getAmountTransaction())
                .category(transaction.getCategory().name())
                .dateTime(transaction.getCreatedAt())
                .idUser(transaction.getUser().getIdUser())
                .build();

        log.info("[INFO] Mapeo de TransactionDto realizado correctamente: {}", transactionDto);
        return transactionDto;
    }

    private static RoleCategory mapCategory (String categoria){
        try {
            RoleCategory roleCategory = RoleCategory.valueOf(categoria.toUpperCase());
            log.info("[INFO] categoria mapeada correctamente: {}:", roleCategory);
            return roleCategory;
        } catch (IllegalArgumentException e){
            log.error("[ERROR] Error al convertir la categoría: '{}'. Categoría inválida.", categoria, e);
            throw new IllegalArgumentException ("Categoria invalida " + categoria);
        }
    }
}
