package com.esteiradev.esteira.events;

import java.util.UUID;

public record CardMovedEvent(
        UUID cardId,
        UUID actorId,
        String oldEsteira,
        String newEsteira
) { }
