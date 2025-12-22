package com.esteiradev.esteira.events;

import com.esteiradev.esteira.enums.EsteiraType;

import java.util.UUID;

public record EsteiraChangedEvent(
        UUID cardId,
        EsteiraType oldEsteira,
        EsteiraType newEsteira,
        String changedBy
) { }
