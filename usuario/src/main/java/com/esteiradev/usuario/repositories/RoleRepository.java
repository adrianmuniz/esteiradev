package com.esteiradev.usuario.repositories;

import com.esteiradev.usuario.enums.RoleType;
import com.esteiradev.usuario.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByRoleName(RoleType roleType);
}
