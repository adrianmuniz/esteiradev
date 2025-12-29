package com.esteiradev.esteira.events;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdatedCardEvent(
        UUID cardId,
        LocalDateTime createdAt,
        UUID changedBy
) { }