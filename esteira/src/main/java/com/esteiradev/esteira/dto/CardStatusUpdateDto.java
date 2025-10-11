package com.esteiradev.esteira.dto;

import com.esteiradev.esteira.enums.CardStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CardStatusUpdateDto {
    @NotNull(message = "Status não pode ser nulo")
    private CardStatus newStatus;

    @NotNull(message = "Esteira não pode ser nulo")
    private UUID esteiraId;
}
