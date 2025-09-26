package com.esteiradev.esteira.dto;

import com.esteiradev.esteira.enums.CardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CardDto {
    @NotBlank(message = "Titulo Obrigatório")
    @Size(min = 3, message = "Titulo deve ter no mínimo 3 caracteres")
    String title;

    String description;

    CardStatus status;

    Integer position;

    UUID esteiraId;

    UUID userId;
}
