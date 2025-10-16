package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.client.UserClient;
import com.esteiradev.esteira.dto.EsteiraDto;
import com.esteiradev.esteira.dto.UpdateEsteiraDto;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.services.EsteiraService;
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

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/esteira")
public class EsteiraController {

    @Autowired
    EsteiraService esteiraService;

    @Autowired
    AcessValidationService acessValidationService;

    @Autowired
    UserClient userClient;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/{userId}/criar")
    public ResponseEntity<Object> criarEsteira(@PathVariable(name = "userId") UUID userId, @Validated @RequestBody EsteiraDto esteiraDto,@RequestHeader("Authorization") String authHeader){
        if(userClient.userExists(userId, authHeader) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var esteiraModel = new EsteiraModel();
        BeanUtils.copyProperties(esteiraDto, esteiraModel);
        esteiraModel.setUserId(userId);
        esteiraService.save(esteiraModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(esteiraService.save(esteiraModel));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<EsteiraModel>> getAllEsteiras(@PageableDefault(page = 0, size = 10, sort = "esteiraId", direction = Sort.Direction.ASC)Pageable pageable) {
        Page<EsteiraModel> esteiraModelPage = null;
        esteiraModelPage = esteiraService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(esteiraModelPage);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{esteiraId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "esteiraId") UUID esteiraId, Authentication authentication){
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else {
            acessValidationService.validateSameUser(esteiraModelOptional.get().getUserId(), authentication);
            return ResponseEntity.status(HttpStatus.OK).body(esteiraModelOptional.get());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{esteiraId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "esteiraId") UUID esteiraId, Authentication authentication){
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        }
        acessValidationService.validateSameUser(esteiraModelOptional.get().getUserId(), authentication);
        esteiraService.delete(esteiraModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Esteira Deletada com Sucesso");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/{esteiraId}")
    public ResponseEntity<Object> update(@PathVariable(value = "esteiraId") UUID esteiraId, @Validated @RequestBody UpdateEsteiraDto updateEsteiraDto, Authentication authentication){
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else {
            acessValidationService.validateSameUser(esteiraModelOptional.get().getUserId(), authentication);
            var esteiraModel = esteiraModelOptional.get();
            esteiraModel.setTitulo(updateEsteiraDto.getTitulo());
            esteiraService.save(esteiraModel);

            return ResponseEntity.status(HttpStatus.OK).body(esteiraModel);
        }
    }

}
