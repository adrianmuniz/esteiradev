package com.esteiradev.esteira.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardCreatedEvent(
        UUID cardId,
        UUID createdBy,
        LocalDateTime createAt
) {}
