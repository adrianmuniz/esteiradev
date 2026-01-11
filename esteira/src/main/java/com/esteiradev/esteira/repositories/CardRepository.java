package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.dto.response.CardResponse;
import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<CardModel, UUID>, JpaSpecificationExecutor<CardModel> {

    @EntityGraph(attributePaths = {"esteira", "sprint"})
    Page<CardModel> findAllByOrderByPriorityAsc(Pageable pageable);

    @EntityGraph(attributePaths = {"esteira", "sprint"})
    Optional<CardModel> findById(UUID id);

    boolean existsBySprint_SprintId(UUID sprintId);

    @Query("""
    select c
    from CardModel c
    join fetch c.esteira e
    left join fetch c.sprint
    where e.userId = :userId
    order by c.priority asc
    """)
    List<CardModel> findAllByUserId(UUID userId);
}
