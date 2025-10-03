package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.SprintModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.Optional;

public interface SprintRepository extends JpaRepository<SprintModel, Id> {
    Optional<SprintModel> findBySprintId(Integer sprintId);
}
