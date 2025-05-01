package com.esteiradev.usuario.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EsteiraDto {

    private UUID esteiraId;
    private String titulo;
    private UUID userId;
}
