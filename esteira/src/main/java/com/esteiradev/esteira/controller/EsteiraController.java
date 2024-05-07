package com.esteiradev.esteira.controller;

import com.esteiradev.esteira.dto.EsteiraDto;
import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.services.EsteiraService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
