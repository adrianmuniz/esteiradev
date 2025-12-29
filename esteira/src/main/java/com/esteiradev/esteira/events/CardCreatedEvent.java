package com.esteiradev.esteira.events;

import java.util.UUID;

public record CardCreatedEvent(
        UUID cardId,
        String title,
        UUID createdBy
) {}
