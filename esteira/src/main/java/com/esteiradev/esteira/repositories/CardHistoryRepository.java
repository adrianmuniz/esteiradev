package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.history.CardHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CardHistoryRepository extends JpaRepository<CardHistory, UUID> {
    @EntityGraph(attributePaths = "changes")
    List<CardHistory> findByCardIdOrderByOccurredAtDesc(UUID cardId);

    @Modifying
    @Query("delete from CardHistory h where h.cardId = :cardId")
    void deleteByCardId(@Param("cardId") UUID cardId);
}
