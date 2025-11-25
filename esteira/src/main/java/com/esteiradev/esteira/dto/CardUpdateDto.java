package com.esteiradev.esteira.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardUpdateDto {

    @Size(min = 3, message = "Titulo deve ter no minimo 3 caracteres", max = 255)
    String title;

    @Size(min = 3, message = "Descrição deve ter no minimo 3 caracteres", max = 255)
    String description;

    private Integer position;

    private Integer estimateHours;

    private Integer sprintId;
}
