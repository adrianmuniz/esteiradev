package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EsteiraUserRepository extends JpaRepository<EsteiraUserModel, UUID>, JpaSpecificationExecutor<EsteiraUserModel> {
}
