package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.dto.UserPasswordUpdateDto;
import com.esteiradev.usuario.dto.UserUpdateDto;
import com.esteiradev.usuario.enums.PasswordUpdateResult;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.service.UserService;
import com.esteiradev.usuario.service.impl.AcessValidationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AcessValidationService acessValidationService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(page =0, size =10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserModel> userModelPage = null;
        userModelPage = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId")UUID userId, Authentication authentication) {
        acessValidationService.validateSameUser(userId, authentication);
        Optional<UserModel> userModelOptional = userService.findByUserId(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId")UUID userId, Authentication authentication) {
        acessValidationService.validateSameUser(userId, authentication);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } else {
            userService.deleteUser(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Usuário Deletado com Sucesso");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{userId}")
    public ResponseEntity<?> updatePartialUser(@PathVariable(value = "userId")UUID userId, @RequestBody UserUpdateDto dto, Authentication authentication) {
        acessValidationService.validateSameUser(userId, authentication);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var userModel = userModelOptional.get();

        if(dto.getName() != null){
            userModel.setName(dto.getName());
        }
        if(dto.getEmail() != null){
            userModel.setEmail(dto.getEmail());
        }
        userModel.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.save(userModel);

        UserUpdateDto responseDto = new UserUpdateDto();
        responseDto.setName(userModel.getName());
        responseDto.setEmail(userModel.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PatchMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable(value = "userId")UUID userId, @RequestBody UserPasswordUpdateDto dto, Authentication authentication){
        acessValidationService.validateSameUser(userId, authentication);

        PasswordUpdateResult result = userService.updatePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        switch (result) {
            case SUCCESS:
                return ResponseEntity.ok("Senha atualizada com sucesso");
            case CURRENT_PASSWORD_INCORRECT:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha atual incorreta");
            case NEW_PASSWORD_SAME_AS_OLD:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nova senha não pode ser igual à atual");
            case IS_EMPTY:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nova senha não pode ser vazia");
            case USER_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
