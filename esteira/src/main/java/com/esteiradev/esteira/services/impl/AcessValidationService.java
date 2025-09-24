package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.configs.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.util.UUID;

@Service
public class AcessValidationService {

    public void validateSameUser(UUID esteiraOwnerId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return;
        }

        if(!userDetails.getUserId().equals(esteiraOwnerId)){
            throw new AccessDeniedException("Acesso Negado!");
        }
    }

}
