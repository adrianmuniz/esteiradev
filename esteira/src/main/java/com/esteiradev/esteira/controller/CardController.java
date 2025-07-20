package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.EsteiraService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/cards")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    EsteiraService esteiraService;

    @PostMapping("/{esteiraId}/create")
    public ResponseEntity<Object> createCard(@PathVariable UUID esteiraId,
                                             @RequestBody CardDto dto){
        var cardModel = new CardModel();
        BeanUtils.copyProperties(dto, cardModel);

        var esteiraModel = esteiraService.findById(esteiraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esteira não encontrada"));
        cardModel.setEsteiraModel(esteiraModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.save(cardModel));
    }

    @GetMapping
    public ResponseEntity<Page<CardModel>> getAllCards(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<CardModel> cardModelPage = null;
        cardModelPage = cardService.findAllWithEsteira(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cardModelPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCard(@PathVariable(value = "id") UUID id){
        Optional<CardModel> optionalCardModel = cardService.findByIdWithEsteira(id);
        if (!optionalCardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalCardModel.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable(value = "id") UUID id){
        Optional<CardModel> cardModel = cardService.findByIdWithEsteira(id);
        if (!cardModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        }
        cardService.delete(cardModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Card Deletado com Sucesso");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCardPartial(@PathVariable(value = "id") UUID id, @RequestBody CardDto dto){
        Optional<CardModel> cardModelOptional = cardService.findByIdWithEsteira(id);
        if (!cardModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card não Encontrado");
        }
        var cardModel = cardModelOptional.get();

        if(dto.title() != null){
            cardModel.setTitle(dto.title());
        }
        if(dto.description() != null){
            cardModel.setDescription(dto.description());
        }
        if(dto.status() != null){
            cardModel.setStatus(dto.status());
        }
        if(dto.position() != null){
            cardModel.setPosition(dto.position());
        }
        if(dto.esteiraId() != null && !dto.esteiraId().equals(cardModel.getEsteiraModel().getEsteiraId())){
            EsteiraModel novaEsteira = esteiraService.findById(dto.esteiraId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esteira não encontrada"));
            cardModel.setEsteiraModel(novaEsteira);
        }
        cardService.save(cardModel);
        return ResponseEntity.status(HttpStatus.OK).body(cardModel);
    }
}
