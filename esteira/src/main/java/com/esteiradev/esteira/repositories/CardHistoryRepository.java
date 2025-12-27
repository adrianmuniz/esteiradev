package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardHistoryRepository extends JpaRepository<CardHistory, UUID> {

}
