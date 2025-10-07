package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.SprintModel;

import java.util.Optional;

public interface SprintService {
    Object save(SprintModel sprint);

    Optional<SprintModel> findBySprintId(Integer sprintId);

    void delete(SprintModel sprintModel);
}
