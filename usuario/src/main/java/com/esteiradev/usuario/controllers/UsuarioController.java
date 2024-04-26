package com.esteiradev.usuario.controllers;

import com.esteiradev.usuario.model.UsuarioModel;
import com.esteiradev.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "x", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }
}
