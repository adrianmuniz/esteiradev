package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.repositories.EsteiraRepository;
import com.esteiradev.esteira.services.EsteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsteiraServiceImpl implements EsteiraService {

    @Autowired
    EsteiraRepository esteiraRepository;
    @Override
    public EsteiraModel save(EsteiraModel esteiraModel) {
        return esteiraRepository.save(esteiraModel);
    }
}
