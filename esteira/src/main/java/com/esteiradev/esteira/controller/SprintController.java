package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.SprintDto;
import com.esteiradev.esteira.model.SprintModel;
import com.esteiradev.esteira.services.SprintService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/esteira/sprint")
public class SprintController {

    @Autowired
    SprintService sprintService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<Object> createSprint(@RequestBody SprintDto sprintDto){
        var sprint = new SprintModel();
        BeanUtils.copyProperties(sprintDto, sprint);
        return ResponseEntity.status(HttpStatus.CREATED).body(sprintService.save(sprint));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{sprintId}")
    public ResponseEntity<Object> getSprint(@PathVariable Integer sprintId){
        Optional<SprintModel> sprintModel = sprintService.findBySprintId(sprintId);
        return ResponseEntity.status(HttpStatus.OK).body(sprintModel.get());
    }
}
