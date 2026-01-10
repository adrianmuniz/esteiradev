package com.esteiradev.esteira.services;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.dto.CardUpdateDto;
import com.esteiradev.esteira.dto.MoveCardDto;
import com.esteiradev.esteira.dto.response.CardResponse;
import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {


    Page<CardModel> getAll(Pageable pageable);

    Optional<CardModel> get(UUID id);

    void delete(CardModel cardModel);

    boolean findBySprintId(UUID sprintId);

    void moveCard(UUID cardId, MoveCardDto dto);

    CardResponse create(UUID esteiraId, CardDto dto);

    CardModel update(UUID cardId, CardUpdateDto dto, Authentication authentication);

    List<CardModel> getWithUserId(UUID userId);
}
