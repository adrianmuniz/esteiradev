package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.EsteiraUserModel;
import com.esteiradev.esteira.repositories.EsteiraUserRepository;
import com.esteiradev.esteira.services.EsteiraUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EsteiraUserServiceImpl implements EsteiraUserService {

    @Autowired
    EsteiraUserRepository esteiraUserRepository;

    @Override
    public EsteiraUserModel save(EsteiraUserModel esteiraUserModel) {
        return esteiraUserRepository.save(esteiraUserModel);
    }
}
