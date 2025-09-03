package com.esteiradev.usuario.service;

import com.esteiradev.usuario.enums.PasswordUpdateResult;
import com.esteiradev.usuario.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {


    Optional<UserModel> findById(UUID userId);

    void deleteUser(UserModel userModel);

    UserModel save(UserModel userModel);

    boolean existsByEmail(String email);

    Page<UserModel> findAll(Pageable pageable);

    Optional<UserModel> findByUserId(UUID userId);

    boolean isCurrent(UUID userId, String email);

    PasswordUpdateResult updatePassword(UUID userId, String oldPassword, String newPassword);
}
