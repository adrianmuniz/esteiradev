package com.esteiradev.esteira.dto;

import com.esteiradev.esteira.enums.EsteiraType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsteiraDto {

    private UUID esteiraId;

    @NotBlank(message = "Nome da esteira não pode ser nulo")
    @Size(min = 3, message = "Nome da esteira deve ter no mínimo 3 caracteres")
    private String titulo;

    private EsteiraType type;

    private UUID userId;
}
