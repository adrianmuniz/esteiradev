package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CardService {

    CardModel save(CardModel cardModel);

    Page<CardModel> findAll(Pageable pageable);

    Optional<CardModel> findById(UUID id);

    void delete(CardModel cardModel);

    boolean findBySprintId(UUID sprintId);
}
