package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EsteiraRepository extends JpaRepository<EsteiraModel, UUID>, JpaSpecificationExecutor<EsteiraModel> {

    @Query("""
    select e
    from EsteiraModel e
    where e.userId = :userId
    order by e.ordem asc
    """)
    List<EsteiraModel> findByUserIdOrderByOrdemAsc(UUID userId);

}
