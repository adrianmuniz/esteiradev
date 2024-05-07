package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.repositories.EsteiraRepository;
import com.esteiradev.esteira.services.EsteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EsteiraServiceImpl implements EsteiraService {

    @Autowired
    EsteiraRepository esteiraRepository;
    @Override
    public EsteiraModel save(EsteiraModel esteiraModel) {
        return esteiraRepository.save(esteiraModel);
    }

    @Override
    public List<EsteiraModel> findAll() {
        return esteiraRepository.findAll();
    }

    @Override
    public Optional<EsteiraModel> findById(UUID esteiraId) {
        return esteiraRepository.findById(esteiraId);
    }

    @Override
    public void delete(EsteiraModel esteiraModel) {
        esteiraRepository.delete(esteiraModel);
    }
}
