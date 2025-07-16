package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardModel, UUID>, JpaSpecificationExecutor<CardModel> {

    @Query("SELECT c FROM CardModel c JOIN FETCH c.esteiraModel")
    Page<CardModel> findAllWithEsteira(Pageable pageable);
}
