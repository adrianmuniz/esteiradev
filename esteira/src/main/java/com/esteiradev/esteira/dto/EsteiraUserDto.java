package com.esteiradev.esteira.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EsteiraUserDto {

    private UUID userId;
    private UUID esteiraId;
}
