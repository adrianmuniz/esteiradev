package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.EsteiraModel;
import com.esteiradev.esteira.repositories.EsteiraRepository;
import com.esteiradev.esteira.services.EsteiraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EsteiraServiceImpl implements EsteiraService {

    @Autowired
    EsteiraRepository esteiraRepository;
    @Transactional
    @Override
    public EsteiraModel save(EsteiraModel esteiraModel) {
        return esteiraRepository.save(esteiraModel);
    }

    @Override
    public Optional<EsteiraModel> findById(UUID esteiraId) {
        return esteiraRepository.findById(esteiraId);
    }

    @Transactional
    @Override
    public void delete(EsteiraModel esteiraModel) {
        esteiraRepository.delete(esteiraModel);
    }

    @Override
    public Page<EsteiraModel> findAll(Pageable pageable) {
        return esteiraRepository.findAll(pageable);
    }

    @Override
    public List<EsteiraModel> findAllByUserId(UUID userId) {
        return esteiraRepository.findByUserIdOrderByOrdemAsc(userId);
    }

    @Override
    public List<EsteiraModel> findBoardByUserId(UUID userId) {
        return esteiraRepository.findBoardByUserId(userId);
    }
}
