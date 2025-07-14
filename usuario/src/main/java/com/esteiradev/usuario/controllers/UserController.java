package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.dto.UserDTO;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.service.UserService;
import com.esteiradev.usuario.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page =0, size =10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                       @RequestParam(required = false) UUID userId) {
        Page<UserModel> userModelPage = null;
        if(userId != null) {
            userModelPage = userService.findAll(SpecificationTemplate.userIdEquals(userId).and(spec), pageable);
        } else {
            userModelPage = userService.findAll(spec, pageable);
        }
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
