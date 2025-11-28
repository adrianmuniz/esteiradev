package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.SprintDto;
import com.esteiradev.esteira.dto.SprintUpdateDto;
import com.esteiradev.esteira.model.SprintModel;
import com.esteiradev.esteira.services.CardService;
import com.esteiradev.esteira.services.SprintService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/esteira/sprint")
public class SprintController {

    @Autowired
    SprintService sprintService;

    @Autowired
    CardService cardService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody SprintDto sprintDto){
        var sprint = new SprintModel();
        BeanUtils.copyProperties(sprintDto, sprint);
        return ResponseEntity.status(HttpStatus.CREATED).body(sprintService.save(sprint));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{sprintId}")
    public ResponseEntity<Object> get(@PathVariable UUID sprintId){
        Optional<SprintModel> sprintModel = sprintService.findBySprintId(sprintId);
        return ResponseEntity.status(HttpStatus.OK).body(sprintModel.get());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{sprintId}")
    public ResponseEntity<Object> delete(@PathVariable UUID sprintId){
        Optional<SprintModel> sprintModel = sprintService.findBySprintId(sprintId);
        if(!sprintModel.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não Encontrado");
        }
        boolean hasCards = cardService.findBySprintId(sprintId);
        if (hasCards) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Não é possível excluir a Sprint com Cards Associados.");
        }

        sprintService.delete(sprintModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Sprint Deletada");
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{sprintId}")
    public ResponseEntity<Object> update(@PathVariable UUID sprintId, @RequestBody SprintUpdateDto dto){
        Optional<SprintModel> sprintModel = sprintService.findBySprintId(sprintId);
        if(!sprintModel.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sprint não Encontrado");
        }
        var sprint = sprintModel.get();
        if(dto.getName() != null){
            sprint.setName(dto.getName());
        }
        if(dto.getStartDate() != null){
            sprint.setStartDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null){
            sprint.setEndDate(dto.getEndDate());
        }
        sprintService.save(sprint);
        return ResponseEntity.status(HttpStatus.OK).body(sprint);
    }
}