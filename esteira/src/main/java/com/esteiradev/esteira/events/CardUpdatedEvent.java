package com.esteiradev.esteira.events;

import com.esteiradev.esteira.model.CardHistoryChange;

import java.util.UUID;
import java.util.List;

public record CardUpdatedEvent(
        UUID cardId,
        UUID actorId,
        List<CardHistoryChange> changes
) { }