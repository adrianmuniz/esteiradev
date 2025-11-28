package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardModel, UUID>, JpaSpecificationExecutor<CardModel> {

    @EntityGraph(attributePaths = {"esteiraModel", "sprint"})
    Page<CardModel> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"esteiraModel", "sprint"})
    Optional<CardModel> findById(UUID id);

    boolean existsBySprint_SprintId(UUID sprintId);
}
