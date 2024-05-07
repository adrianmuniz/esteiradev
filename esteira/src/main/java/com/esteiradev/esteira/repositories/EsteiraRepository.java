package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EsteiraRepository extends JpaRepository<EsteiraModel, UUID> {
}
