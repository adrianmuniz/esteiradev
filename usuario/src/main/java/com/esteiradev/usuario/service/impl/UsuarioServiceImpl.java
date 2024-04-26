package com.esteiradev.usuario.service.impl;

import com.esteiradev.usuario.model.UsuarioModel;
import com.esteiradev.usuario.repositories.UsuarioRepository;
import com.esteiradev.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioModel> findAll() {return usuarioRepository.findAll(); }

    @Override
    public Optional<UsuarioModel> findById(UUID userId) {
        return usuarioRepository.findById(userId);
    }
}
