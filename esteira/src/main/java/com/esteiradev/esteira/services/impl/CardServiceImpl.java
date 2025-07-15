package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.repositories.CardRepository;
import com.esteiradev.esteira.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public CardModel save(CardModel cardModel) {
        return cardRepository.save(cardModel);
    }
}
