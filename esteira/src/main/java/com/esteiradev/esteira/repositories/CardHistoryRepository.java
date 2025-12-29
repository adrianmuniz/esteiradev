package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardHistoryRepository extends JpaRepository<CardHistory, UUID> {
    @EntityGraph(attributePaths = "changes")
    List<CardHistory> findByCardIdOrderByOccurredAtDesc(UUID cardId);
}
