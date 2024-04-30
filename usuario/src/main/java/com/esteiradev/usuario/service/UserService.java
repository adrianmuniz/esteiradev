package com.esteiradev.usuario.service;

import com.esteiradev.usuario.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void deleteUser(UserModel userModel);

    UserModel save(UserModel userModel);

    boolean existsByEmail(String email);
}
