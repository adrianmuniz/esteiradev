package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.dto.UserDTO;
import com.esteiradev.usuario.enums.RoleType;
import com.esteiradev.usuario.enums.UserStatus;
import com.esteiradev.usuario.enums.UserType;
import com.esteiradev.usuario.model.RoleModel;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.service.RoleService;
import com.esteiradev.usuario.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/singup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }
        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUserType(UserType.USER);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setDateCreate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(roleModel);
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @PostMapping("/singup/admin")
    public ResponseEntity<Object> registerUserAdmin(@RequestBody @Validated(UserDTO.UserView.RegistrationPost.class) @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO) {
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }
        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is Not Found."));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        userModel.setUserType(UserType.ADMIN);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setDateCreate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(roleModel);
        userService.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }



}
