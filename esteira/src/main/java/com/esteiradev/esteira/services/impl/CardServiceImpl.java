package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.dto.MoveCardDto;
import com.esteiradev.esteira.enums.EsteiraType;
import com.esteiradev.esteira.enums.StatusCard;
import com.esteiradev.esteira.events.EsteiraChangedEvent;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.repositories.CardHistoryRepository;
import com.esteiradev.esteira.repositories.CardRepository;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.EsteiraService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    EsteiraService esteiraService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardHistoryRepository  cardHistoryRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public CardModel save(CardModel cardModel) {
        return cardRepository.save(cardModel);
    }

    @Override
    public Page<CardModel> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public Optional<CardModel> findById(UUID id) {
        return cardRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(CardModel cardModel) {
        cardRepository.delete(cardModel);
    }

    @Override
    public boolean findBySprintId(UUID sprintId) {
        return cardRepository.existsBySprint_SprintId(sprintId);
    }

    @Transactional
    @Override
    public void moveCard(UUID cardId, MoveCardDto dto) {
        Optional<CardModel> cardOpt = findById(cardId);
        Optional<EsteiraModel> esteiraOpt = esteiraService.findById(dto.getEsteiraId());
        if(cardOpt.isEmpty() || esteiraOpt.isEmpty()){
            throw new RuntimeException("Valide os campos! Status e Esteira id Obrigatórios");
        }
        EsteiraType atual = cardOpt.get().getEsteiraModel().getType();
        int nova = esteiraOpt.get().getType().getOrdem();

        if(!atual.canMove(nova)){
            throw new IllegalStateException("Movimentação inválida");
        }
        if(nova == 5){
            closeCard(cardOpt.get());
        } else if(atual.getOrdem() == 5 && nova != 5){
            reopenCard(cardOpt.get(), nova);
        }

        var card = cardOpt.get();
        card.setEsteiraModel(esteiraOpt.get());
        save(card);
        eventPublisher.publishEvent(new EsteiraChangedEvent(
                        cardId,
                        atual,
                        card.getEsteiraModel().getType(),
                        null
                )
        );
    }

    @Transactional
    private void reopenCard(CardModel cardModel,int nova) {
        cardModel.setStatus(StatusCard.TEST);
        cardModel.setDateUpdated(LocalDateTime.now());
        cardModel.setDateResolved(null);
    }

    @Transactional
    public void closeCard(CardModel cardModel){
        cardModel.setStatus(StatusCard.FECHADO);
        cardModel.setDateResolved(LocalDateTime.now());
    }
}
