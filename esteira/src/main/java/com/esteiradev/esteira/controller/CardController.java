package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.dto.MoveCardDto;
import com.esteiradev.esteira.dto.CardUpdateDto;
import com.esteiradev.esteira.enums.StatusCard;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.model.SprintModel;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.EsteiraService;
import com.esteiradev.esteira.services.SprintService;
import com.esteiradev.esteira.services.impl.AcessValidationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/esteira/cards")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    EsteiraService esteiraService;

    @Autowired
    SprintService sprintService;

    @Autowired
    AcessValidationService acessValidationService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/{esteiraId}/create")
    public ResponseEntity<Object> create(@PathVariable UUID esteiraId,
                                             @Validated @RequestBody CardDto dto){
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

        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardModel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CardModel>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<CardModel> cardModelPage = null;
        cardModelPage = cardService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardModelPage);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(value = "id") UUID cardId, Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findById(cardId);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        } else {
            acessValidationService.validateSameUser(optionalCardModel.get().getUserId(), authentication);
            return ResponseEntity.status(HttpStatus.OK).body(optionalCardModel.get());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID cardId,Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findById(cardId);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        }
        acessValidationService.validateSameUser(optionalCardModel.get().getUserId(), authentication);
        cardService.delete(optionalCardModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Card Deletado com Sucesso");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID cardId, @Validated @RequestBody CardUpdateDto dto, Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findById(cardId);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        }
        acessValidationService.validateSameUser(optionalCardModel.get().getUserId(), authentication);
        var cardModel = optionalCardModel.get();
        if(dto.getTitle() != null){
            cardModel.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            cardModel.setDescription(dto.getDescription());
        }
        if(dto.getPosition() != null){
            cardModel.setPosition(dto.getPosition());
        }
        if(dto.getEstimateHours() != null){
            cardModel.setEstimateHours(dto.getEstimateHours());
        }
        if(dto.getSprintId() != null){
            Optional<SprintModel> sprintOpt = sprintService.findBySprintId(dto.getSprintId());
            if(!sprintOpt.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não Encontrada");
            }
            cardModel.setSprint(sprintOpt.get());
        }
        cardModel.setDateUpdated(LocalDateTime.now());
        cardService.save(cardModel);
        return ResponseEntity.status(HttpStatus.OK).body(cardModel);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{id}/move")
    public ResponseEntity<Object> moveCard(@PathVariable(value = "id") UUID cardId, @RequestBody MoveCardDto dto){
        Optional<CardModel> cardOpt = cardService.findById(cardId);
        Optional<EsteiraModel> esteiraOpt = esteiraService.findById(dto.getEsteiraId());
        if(cardOpt.isEmpty() || esteiraOpt.isEmpty()){
            throw new RuntimeException("Valide os campos! Status e Esteira id Obrigatórios");
        }
        var card = cardOpt.get();
        card.getEsteiraModel().setEsteiraId(dto.getEsteiraId());
        cardService.save(card);
        return ResponseEntity.ok().build();
    }
}
