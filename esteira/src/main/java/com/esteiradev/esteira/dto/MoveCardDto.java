package com.esteiradev.esteira.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MoveCardDto {
    @NotNull(message = "Esteira não pode ser nulo")
    private UUID esteiraId;
}
