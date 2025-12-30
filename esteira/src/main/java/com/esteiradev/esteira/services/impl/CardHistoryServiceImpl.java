package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.CardHistory;
import com.esteiradev.esteira.repositories.CardHistoryRepository;
import com.esteiradev.esteira.services.CardHistoryService;
import com.esteiradev.esteira.utils.CardHistoryMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CardHistoryServiceImpl implements CardHistoryService {

    @Autowired
    CardHistoryRepository repository;

    @Override
    public List<CardHistory> getHistory(UUID cardId) {
        List<CardHistory> historyList = repository.findByCardIdOrderByOccurredAtDesc(cardId);
        historyList.forEach(h ->
                h.setMessage(CardHistoryMessageBuilder.build(h)));

        return historyList;
    }
}
