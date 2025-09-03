package com.esteiradev.usuario.configs.security;

import com.esteiradev.usuario.model.UserModel;
import com.esteiradev.usuario.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
        return UserDetailsImpl.build(userModel);
    }

    public UserDetails loadUserById(UUID userId) throws AuthenticationCredentialsNotFoundException {
        UserModel userModel = userRepository.findByUserId(userId)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User Not Found with userId: " + userId));
        return UserDetailsImpl.build(userModel);
    }
}
