package com.esteiradev.esteira.repositories;

import com.esteiradev.esteira.model.EsteiraModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EsteiraRepository extends JpaRepository<EsteiraModel, UUID>, JpaSpecificationExecutor<EsteiraModel> {

}
