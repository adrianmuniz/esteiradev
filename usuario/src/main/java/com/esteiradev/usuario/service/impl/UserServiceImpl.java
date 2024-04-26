package com.esteiradev.usuario.service.impl;

import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.repositories.UserRepository;
import com.esteiradev.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository usuarioRepository;

    public List<UserModel> findAll() {return usuarioRepository.findAll(); }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return usuarioRepository.findById(userId);
    }
}
