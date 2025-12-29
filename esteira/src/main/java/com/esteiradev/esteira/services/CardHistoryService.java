package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.CardHistory;

import java.util.List;
import java.util.UUID;

public interface CardHistoryService {

    List<CardHistory> getHistory(UUID cardId);
}
