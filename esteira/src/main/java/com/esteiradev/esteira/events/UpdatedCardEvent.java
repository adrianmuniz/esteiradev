package com.esteiradev.esteira.events;

import java.util.UUID;

public record UpdatedCardEvent(
        UUID cardId,
        UUID changedBy
) { }