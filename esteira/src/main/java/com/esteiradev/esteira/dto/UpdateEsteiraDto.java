package com.esteiradev.esteira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEsteiraDto {
    @NotBlank(message = "Nome da esteira não pode ser nulo")
    @Size(min = 3, message = "Nome da esteira deve ter no mínimo 3 caracteres")
    private String titulo;
}
