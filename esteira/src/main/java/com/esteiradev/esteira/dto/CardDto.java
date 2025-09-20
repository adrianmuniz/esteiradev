package com.esteiradev.esteira.dto;

import com.esteiradev.esteira.enums.CardStatus;

import java.util.UUID;

public record CardDto(
            String title,
            String description,
            CardStatus status,
            Integer position,
            UUID esteiraId,
            UUID userId
) {}