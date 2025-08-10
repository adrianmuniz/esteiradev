package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.configs.security.UserDetailsImpl;
import com.esteiradev.usuario.dto.UserDTO;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.service.UserService;
import com.esteiradev.usuario.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(@PageableDefault(page =0, size =10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserModel> userModelPage = null;
        userModelPage = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId")UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId")UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            userService.deleteUser(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted succes");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId")UUID userId, @RequestBody @Validated(UserDTO.UserView.UserPut.class) @JsonView({UserDTO.UserView.UserPut.class})UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        } else {
            var userModel = userModelOptional.get();
            userModel.setName(userDTO.getName());
            userModel.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.save(userModel);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

}
