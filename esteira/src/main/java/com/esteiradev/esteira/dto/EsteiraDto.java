package com.esteiradev.esteira.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsteiraDto {

    private UUID esteiraId;

    @NotBlank
    private String titulo;
}
