package com.esteiradev.esteira.services;

import com.esteiradev.esteira.model.EsteiraModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EsteiraService {
    EsteiraModel save(EsteiraModel esteiraModel);

    List<EsteiraModel> findAll();

    Optional<EsteiraModel> findById(UUID esteiraId);

    void delete(EsteiraModel esteiraModel);

    Page<EsteiraModel> findAll(Specification<EsteiraModel> spec, Pageable pageable);
}
