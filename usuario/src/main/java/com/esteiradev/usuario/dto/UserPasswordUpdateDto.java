package com.esteiradev.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordUpdateDto {

    @NotBlank(message = "A senha antiga é obrigatória.")
    private String oldPassword;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 8, max = 20, message = "A nova senha deve ter entre 8 e 20 caracteres.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "A nova senha deve conter ao menos uma letra maiúscula, uma letra minúscula e um número."
    )
    private String newPassword;
}
