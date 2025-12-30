package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.dto.MoveCardDto;
import com.esteiradev.esteira.dto.CardUpdateDto;
import com.esteiradev.esteira.model.history.CardHistory;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.services.CardHistoryService;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.impl.AcessValidationService;
import lombok.extern.log4j.Log4j2;
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

import java.util.List;
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
    CardHistoryService cardHistoryService;

    @Autowired
    AcessValidationService acessValidationService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/{esteiraId}/create")
    public ResponseEntity<Object> create(@PathVariable UUID esteiraId,
                                             @Validated @RequestBody CardDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(esteiraId, dto));
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

        CardModel card = cardService.updateCard(cardId, dto, authentication);
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{id}/move")
    public ResponseEntity<Object> moveCard(@PathVariable(value = "id") UUID cardId, @RequestBody MoveCardDto dto){
        cardService.moveCard(cardId, dto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{cardId}/history")
    public ResponseEntity<List<CardHistory>> history(@PathVariable UUID cardId) {
        return ResponseEntity.ok(cardHistoryService.getHistory(cardId));
    }
}
