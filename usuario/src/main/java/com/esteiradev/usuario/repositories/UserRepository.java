package com.esteiradev.usuario.repositories;

import com.esteiradev.usuario.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<UserModel> findByUserId(UUID id);

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Page<UserModel> findAll(Pageable pageable);
}
