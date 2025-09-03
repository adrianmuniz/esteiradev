package com.esteiradev.usuario.dto;

import lombok.Data;

@Data
public class UserPasswordUpdateDto {

    private String oldPassword;
    private String newPassword;
}
