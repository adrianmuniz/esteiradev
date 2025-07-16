package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.CardDto;
import com.esteiradev.esteira.model.CardModel;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.EsteiraService;
import com.esteiradev.esteira.specifications.SpecificationTemplate;
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

}
