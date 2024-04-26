package com.esteiradev.usuario.service;

import com.esteiradev.usuario.model.UsuarioModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

    List<UsuarioModel> findAll();

    Optional<UsuarioModel> findById(UUID userId);
}
