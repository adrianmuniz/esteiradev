package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.history.CardHistoryChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CardHistoryChangeRepository
        extends JpaRepository<CardHistoryChange, UUID> {

    @Modifying
    @Query("""
        delete from CardHistoryChange c
        where c.history.id in (
            select h.id from CardHistory h where h.cardId = :cardId
        )
    """)
    void deleteByCardId(@Param("cardId") UUID cardId);
}
