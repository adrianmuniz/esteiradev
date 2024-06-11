package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.clients.UserClient;
import com.esteiradev.usuario.dto.EsteiraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
public class UserEsteiraController {

    @Autowired
    UserClient esteiraClient;

    @GetMapping("/users/{userId}/esteiras")
    public ResponseEntity<Page<EsteiraDto>> getAllEsteirasByUser(@PageableDefault(page =0, size =10, sort = "esteiraId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(esteiraClient.getAllEsteirasByUser(userId, pageable));
    }
}
