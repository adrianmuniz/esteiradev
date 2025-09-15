package com.esteiradev.usuario.service.impl;

import com.esteiradev.usuario.configs.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.UUID;

@Service
public class AcessValidationService {
    public void validateSameUser(UUID userId, Authentication authentication)  {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return;
        }

        if(!userDetails.getUserId().equals(userId)){
            throw new AccessDeniedException("Acesso Negado!");
        }
    }
}
