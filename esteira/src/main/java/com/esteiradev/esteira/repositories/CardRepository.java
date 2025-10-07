package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardModel, UUID>, JpaSpecificationExecutor<CardModel> {

    @Query(
            value = "SELECT c FROM CardModel c JOIN FETCH c.esteiraModel",
            countQuery = "SELECT COUNT(c) FROM CardModel c"
    )
    Page<CardModel> findAllWithEsteira(Pageable pageable);

    @Query("SELECT c FROM CardModel c JOIN FETCH c.esteiraModel WHERE c.id = :id")
    Optional<CardModel> findByIdWithEsteira(UUID id);

    boolean existsBySprint_SprintId(Integer sprintId);
}
