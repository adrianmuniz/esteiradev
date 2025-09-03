package com.esteiradev.usuario.service.impl;

import com.esteiradev.usuario.enums.PasswordUpdateResult;
import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.repositories.UserRepository;
import com.esteiradev.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void deleteUser(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserModel> findByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public boolean isCurrent(UUID userId, String email) {
        return userRepository.findById(userId)
                .map(user -> user.getEmail().equals(email))
                .orElse(false);
    }

    @Override
    public PasswordUpdateResult updatePassword(UUID userId, String oldPassword, String newPassword) {
        Optional<UserModel> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return PasswordUpdateResult.USER_NOT_FOUND;
        }
        UserModel user = userOpt.get();

        //validacoes
        if(newPassword.isEmpty()){
            return PasswordUpdateResult.IS_EMPTY;
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return PasswordUpdateResult.CURRENT_PASSWORD_INCORRECT;
        }
        if(passwordEncoder.matches(newPassword, user.getPassword())){
            return PasswordUpdateResult.NEW_PASSWORD_SAME_AS_OLD;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(user);
        return PasswordUpdateResult.SUCCESS;
    }
}
