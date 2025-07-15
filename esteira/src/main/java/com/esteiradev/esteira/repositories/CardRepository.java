package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardModel, UUID> {
}
