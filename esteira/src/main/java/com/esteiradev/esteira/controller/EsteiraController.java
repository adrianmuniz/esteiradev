package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.EsteiraDto;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.services.EsteiraService;
import com.esteiradev.esteira.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/esteira")
public class EsteiraController {

    @Autowired
    EsteiraService esteiraService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarEsteira(@RequestBody @Validated EsteiraDto esteiraDto){
        var esteiraModel = new EsteiraModel();
        BeanUtils.copyProperties(esteiraDto, esteiraModel);
        esteiraService.save(esteiraModel);
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
        Optional<EsteiraModel> esteiraModelOptional = esteiraService.findById(esteiraId);
        if (!esteiraModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Esteira não encontrada");
        } else {
            esteiraService.delete(esteiraModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Esteira Deletada com Sucesso");
        }
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
