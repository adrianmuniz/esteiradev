package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.EsteiraUserModel;

import java.util.Optional;
import java.util.UUID;


public interface EsteiraUserService {
    void save(EsteiraUserModel esteiraUserModel);

    void  delete (EsteiraUserModel esteiraUserModel);

    Optional<EsteiraUserModel> findByEsteiraId(UUID esteiraId);
}
