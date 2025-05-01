package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.clients.EsteiraClient;
import com.esteiradev.usuario.configs.security.UserDetailsImpl;
import com.esteiradev.usuario.dto.EsteiraDto;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
public class UserEsteiraController {

    @Autowired
    EsteiraClient esteiraClient;

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}/esteiras")
    public ResponseEntity<Page<EsteiraDto>> getAllEsteirasByUser(@PageableDefault(page =0, size =10, sort = "esteiraId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(esteiraClient.getAllEsteirasByUser(userId, pageable));
    }

    @PostMapping("/users/criarEsteira")
    public ResponseEntity<Object> criarEsteiraByUser(Authentication authentication, @RequestBody EsteiraDto esteiraDto){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        UUID userId = userPrincipal.getUserId();
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(esteiraClient.createEsteiraByUser(userId, esteiraDto));
    }

}
