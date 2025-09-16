package com.esteiradev.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotBlank(message = "Nome não pode ser Vazio")
    private String name;

    @NotBlank(message = "Email não pode ser Nulo")
    @Email(message = "Email Inválido")
    private String email;
}
