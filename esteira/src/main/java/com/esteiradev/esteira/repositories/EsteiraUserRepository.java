package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EsteiraUserRepository extends JpaRepository<EsteiraUserModel, UUID>, JpaSpecificationExecutor<EsteiraUserModel> {

    @Query(value = "SELECT * FROM TB_ESTEIRAS_USER WHERE esteira_id = :esteiraId", nativeQuery = true)
    Optional<EsteiraUserModel> findByEsteiraId(UUID esteiraId);
}
