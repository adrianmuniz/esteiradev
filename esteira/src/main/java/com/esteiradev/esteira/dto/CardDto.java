package com.esteiradev.esteira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CardDto {
    @NotBlank(message = "Titulo Obrigatório")
    @Size(min = 3, message = "Titulo deve ter no mínimo 3 caracteres", max = 50)
    String title;

    @NotNull(message = "Descrição Obrigatória")
    @Size(min = 5, message = "Descrição deve ter no mínimo 5 caracteres", max = 255)
    String description;

    private Integer estimateHours;

    private Integer sprintId;

    private UUID userId;
}
