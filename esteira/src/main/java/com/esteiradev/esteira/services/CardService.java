package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    CardModel save(CardModel cardModel);

    Page<CardModel> findAllWithEsteira(Pageable pageable);
}
