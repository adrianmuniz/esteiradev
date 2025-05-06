package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.EsteiraDto;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.model.EsteiraUserModel;
import com.esteiradev.esteira.services.EsteiraService;
import com.esteiradev.esteira.services.EsteiraUserService;
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
    EsteiraUserService esteiraUserService;

    @PostMapping("/{userId}/criar")
    public ResponseEntity<Object> criarEsteira(@PathVariable(name = "userId") UUID userId,@RequestBody @Validated EsteiraDto esteiraDto){
        var esteiraModel = new EsteiraModel();
        BeanUtils.copyProperties(esteiraDto, esteiraModel);
        esteiraModel.setUserId(userId);
        esteiraService.save(esteiraModel);

        var esteiraUserModel = new EsteiraUserModel();
        esteiraUserModel.setUserId(userId);
        esteiraUserModel.setEsteiraId(esteiraModel.getEsteiraId());
        esteiraUserService.save(esteiraUserModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(esteiraModel);
    }

    @GetMapping
    public ResponseEntity<Page<EsteiraModel>> getAll(SpecificationTemplate.EsteiraSpec spec,
                                                     @PageableDefault(page = 0, size = 10, sort = "esteiraId", direction = Sort.Direction.ASC)Pageable pageable,
                                                     @RequestParam(required = false) UUID userId) {
        Page<EsteiraModel> esteiraModelPage = null;
        if(userId != null){
            esteiraModelPage = esteiraService.findAll(SpecificationTemplate.esteiraUsersId(userId).and(spec), pageable);
        } else {
            esteiraModelPage = esteiraService.findAll(spec, pageable);
        }
        return ResponseEntity.status(HttpStatus.OK).body(esteiraModelPage);
    }

    @GetMapping("/{esteiraId}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "esteiraId") UUID esteiraId){
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(esteiraModelOptional.get());
        }
    }

    @DeleteMapping("/{esteiraId}")
    public ResponseEntity<Object> delete(@PathVariable(value = "esteiraId") UUID esteiraId){
        Optional<EsteiraModel> esteira = esteiraService.findById(esteiraId);

        Optional<EsteiraUserModel> esteiraUser = esteiraUserService.findByEsteiraId(esteiraId);
        log.debug("esteiraModelOptional encontrado: " + esteiraUser);
        if (!esteiraUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else if(esteiraUser.isPresent()){
            esteiraUserService.delete(esteiraUser.get());
            log.info("esteiraUser deletada");
        }
        esteiraService.delete(esteira.get());
        return ResponseEntity.status(HttpStatus.OK).body("Esteira Deletada com Sucesso");
    }

    @PutMapping("/{esteiraId}")
    public ResponseEntity<Object> update(@PathVariable(value = "esteiraId") UUID esteiraId, @RequestBody EsteiraDto esteiraDto){
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else {
            var esteiraModel = esteiraModelOptional.get();
            esteiraModel.setTitulo(esteiraDto.getTitulo());
            esteiraService.save(esteiraModel);

            return ResponseEntity.status(HttpStatus.OK).body(esteiraModel);
        }
    }

}
