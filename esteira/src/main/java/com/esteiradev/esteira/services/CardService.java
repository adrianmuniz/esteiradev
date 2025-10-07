package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CardService {

    CardModel save(CardModel cardModel);

    Page<CardModel> findAllWithEsteira(Pageable pageable);

    Optional<CardModel> findByIdWithEsteira(UUID id);

    void delete(CardModel cardModel);

    boolean findBySprintId(Integer sprintId);
}
