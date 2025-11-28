package com.esteiradev.esteira.services.impl;

import com.esteiradev.esteira.model.SprintModel;
import com.esteiradev.esteira.repositories.SprintRepository;
import com.esteiradev.esteira.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SprintServiceImpl implements SprintService {

    @Autowired
    SprintRepository sprintRepository;

    @Override
    public Object save(SprintModel sprint) {
        return sprintRepository.save(sprint);
    }

    @Override
    public Optional<SprintModel> findBySprintId(UUID sprintId) {
        return sprintRepository.findBySprintId(sprintId);
    }

    @Override
    public void delete(SprintModel sprintModel) {
        sprintRepository.delete(sprintModel);
    }
}
