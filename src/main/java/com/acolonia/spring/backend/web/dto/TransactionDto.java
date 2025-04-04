package com.acolonia.spring.backend.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private Long idTransaction;

    @Size(max = 50)
    private String nameTransaction;

    @Size(max = 250)
    private String descriptionTransaction;

    private Double amountTransaction;
    private String category;
    private LocalDateTime dateTime;
    private Long idUser;

}
