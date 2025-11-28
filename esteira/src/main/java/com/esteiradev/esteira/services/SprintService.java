package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.SprintModel;

import java.util.Optional;
import java.util.UUID;

public interface SprintService {
    Object save(SprintModel sprint);

    Optional<SprintModel> findBySprintId(UUID sprintId);

    void delete(SprintModel sprintModel);
}
