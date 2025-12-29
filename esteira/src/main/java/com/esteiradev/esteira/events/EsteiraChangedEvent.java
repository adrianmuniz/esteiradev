package com.esteiradev.esteira.events;

import com.esteiradev.esteira.enums.EsteiraType;

import java.time.LocalDateTime;
import java.util.UUID;

public record EsteiraChangedEvent(
        UUID cardId,
        UUID changedBy,
        EsteiraType oldEsteira,
        EsteiraType newEsteira,
        LocalDateTime createAt
) { }
