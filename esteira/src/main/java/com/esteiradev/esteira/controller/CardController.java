package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.dto.CardUpdateDto;
import com.esteiradev.esteira.enums.CardStatus;
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
    public ResponseEntity<Object> createCard(@PathVariable UUID esteiraId,
                                             @Validated @RequestBody CardDto dto){
        var esteiraModel = esteiraService.findById(esteiraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esteira não encontrada"));

        Optional<SprintModel> sprintModel = sprintService.findBySprintId(dto.getSprintId());
        var cardModel = new CardModel();
        cardModel.setSprint(sprintModel.get());
        cardModel.setEsteiraModel(esteiraModel);
        cardModel.setStatus(CardStatus.TODO);
        cardModel.setPosition(1);
        cardModel.setDateCreate(LocalDateTime.now());
        BeanUtils.copyProperties(dto, cardModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardModel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<CardModel>> getAllCards(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<CardModel> cardModelPage = null;
        cardModelPage = cardService.findAllWithEsteira(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardModelPage);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCard(@PathVariable(value = "id") UUID cardId, Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findByIdWithEsteira(cardId);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        } else {
            acessValidationService.validateSameUser(optionalCardModel.get().getUserId(), authentication);
            return ResponseEntity.status(HttpStatus.OK).body(optionalCardModel.get());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable(value = "id") UUID cardId,Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findByIdWithEsteira(cardId);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        }
        acessValidationService.validateSameUser(optionalCardModel.get().getUserId(), authentication);
        cardService.delete(optionalCardModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Card Deletado com Sucesso");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCardPartial(@PathVariable(value = "id") UUID cardId, @Validated @RequestBody CardUpdateDto dto, Authentication authentication){
        Optional<CardModel> optionalCardModel = cardService.findByIdWithEsteira(cardId);
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
        if(dto.getStatus() != null){
            cardModel.setStatus(dto.getStatus());
        }
        if(dto.getPosition() != null){
            cardModel.setPosition(dto.getPosition());
        }
        if(dto.getEsteiraId() != null && !dto.getEsteiraId().equals(cardModel.getEsteiraModel().getEsteiraId())){
            EsteiraModel novaEsteira = esteiraService.findById(dto.getEsteiraId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esteira não encontrada"));
            cardModel.setEsteiraModel(novaEsteira);
        }
        cardService.save(cardModel);
        return ResponseEntity.status(HttpStatus.OK).body(cardModel);
    }
}
