package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.repositories.CardRepository;
import com.esteiradev.esteira.services.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Transactional
    @Override
    public CardModel save(CardModel cardModel) {
        return cardRepository.save(cardModel);
    }

    @Override
    public Page<CardModel> findAllWithEsteira(Pageable pageable) {
        return cardRepository.findAllWithEsteira(pageable);
    }

    @Override
    public Optional<CardModel> findByIdWithEsteira(UUID id) {
        return cardRepository.findByIdWithEsteira(id);
    }

    @Transactional
    @Override
    public void delete(CardModel cardModel) {
        cardRepository.delete(cardModel);
    }

    @Override
    public boolean findBySprintId(Integer sprintId) {
        return cardRepository.existsBySprint_SprintId(sprintId);
    }
}
