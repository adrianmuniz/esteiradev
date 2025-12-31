package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.dto.CardUpdateDto;
import com.esteiradev.esteira.dto.MoveCardDto;
import com.esteiradev.esteira.enums.CardField;
import com.esteiradev.esteira.enums.EsteiraType;
import com.esteiradev.esteira.enums.StatusCard;
import com.esteiradev.esteira.events.CardCreatedEvent;
import com.esteiradev.esteira.events.CardMovedEvent;
import com.esteiradev.esteira.events.CardUpdatedEvent;
import com.esteiradev.esteira.exceptions.NotFoundException;
import com.esteiradev.esteira.model.history.CardHistoryChange;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.model.SprintModel;
import com.esteiradev.esteira.repositories.CardRepository;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.EsteiraService;
import com.esteiradev.esteira.services.SprintService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    EsteiraService esteiraService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    SprintService  sprintService;

    @Autowired
    AcessValidationService acessValidationService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public CardModel create(UUID esteiraId, CardDto dto) {
        var esteiraModel = esteiraService.findById(esteiraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esteira não encontrada"));

        Optional<SprintModel> sprintModel = sprintService.findBySprintId(dto.getSprintId());
        var cardModel = new CardModel();
        if(dto.getSprintId() != null){
            cardModel.setSprint(sprintModel.get());
        }
        cardModel.setEsteiraModel(esteiraModel);
        cardModel.setStatus(StatusCard.TODO);
        cardModel.setPosition(0);
        cardModel.setDateCreate(LocalDateTime.now());
        cardModel.setHoursUsed(0);
        cardModel.setHoursRemainning(dto.getEstimateHours());
        BeanUtils.copyProperties(dto, cardModel);
        cardRepository.save(cardModel);

        eventPublisher.publishEvent(new CardCreatedEvent(cardModel.getId(), cardModel.getUserId()));
        return cardModel;
    }

    @Transactional
    @Override
    public CardModel update(UUID cardId, CardUpdateDto dto, Authentication authentication) {
        CardModel card = cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("Card não encontrado"));
        acessValidationService.validateSameUser(card.getUserId(), authentication);
        List<CardHistoryChange> changes = new ArrayList<>();

        if(dto.getTitle() != null){
            changes.add(change(CardField.TITLE.name(), card.getTitle(), dto.getTitle()));
            card.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            changes.add(change(CardField.DESCRIPTION.name(), card.getDescription(), dto.getDescription()));
            card.setDescription(dto.getDescription());
        }
        if(dto.getPosition() != null){
            changes.add(change(CardField.POSITION.name(), card.getPosition(), dto.getPosition()));
            card.setPosition(dto.getPosition());
        }
        if(dto.getEstimateHours() != null){
            changes.add(change(CardField.ESTIMATE_HOURS.name(),
                    String.valueOf(card.getEstimateHours()),
                    String.valueOf(dto.getEstimateHours())));
            card.setEstimateHours(dto.getEstimateHours());
        }
        if(dto.getSprintId() != null){
            SprintModel sprint = sprintService.findBySprintId(dto.getSprintId()).orElseThrow(() -> new NotFoundException("Sprint não encontrada"));
            changes.add(change(CardField.SPRINT.name(), card.getSprint().getSprintId(), dto.getSprintId()));
            card.setSprint(sprint);
        }
        card.setDateUpdated(LocalDateTime.now());
        cardRepository.save(card);

        if (!changes.isEmpty()) {
            eventPublisher.publishEvent(
                    new CardUpdatedEvent(cardId, card.getUserId(), changes, LocalDateTime.now())
            );
        }

        return card;
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
        String esteiraAtual = card.getEsteiraModel().getTitulo();
        String novaEsteira = esteiraOpt.get().getTitulo();
        card.setEsteiraModel(esteiraOpt.get());
        cardRepository.save(card);
        eventPublisher.publishEvent(new CardMovedEvent(card.getId(), card.getUserId(), esteiraAtual, novaEsteira));
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

    private CardHistoryChange change(String field, Object oldV, Object newV) {
        return CardHistoryChange.builder()
                .fieldName(field)
                .oldValue(oldV == null ? null : oldV.toString())
                .newValue(newV == null ? null : newV.toString())
                .build();
    }

    @Transactional
    @Override
    public void delete(CardModel cardModel) {
        cardRepository.delete(cardModel);
    }

    @Override
    public Page<CardModel> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public Optional<CardModel> findById(UUID id) {
        return cardRepository.findById(id);
    }

    @Override
    public boolean findBySprintId(UUID sprintId) {
        return cardRepository.existsBySprint_SprintId(sprintId);
    }
}
