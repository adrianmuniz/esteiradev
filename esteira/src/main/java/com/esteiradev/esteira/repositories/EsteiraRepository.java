package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EsteiraRepository extends JpaRepository<EsteiraModel, UUID>, JpaSpecificationExecutor<EsteiraModel> {

    @Query("select e from EsteiraModel e where e.userId = :userId")
    List<EsteiraModel> findByUserIdOrderByOrdemAsc(UUID userId);

    @Query("""
    select distinct e from EsteiraModel e
    left join fetch e.cards c
    where e.userId = :userId
    order by e.ordem
    """)
    List<EsteiraModel> findBoardByUserId(@Param("userId") UUID userId);
}
