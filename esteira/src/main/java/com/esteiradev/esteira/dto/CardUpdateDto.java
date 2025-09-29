package com.esteiradev.esteira.dto;

import com.esteiradev.esteira.enums.CardStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CardUpdateDto {
    @Size(min = 3, message = "Titulo deve ter no minimo 3 caracteres", max = 255)
    String title;

    @Size(min = 3, message = "Descrição deve ter no minimo 3 caracteres", max = 255)
    String description;

    @NotNull(message = "Defina um Status para o Card")
    CardStatus status;

    Integer position;

    UUID esteiraId;
}
