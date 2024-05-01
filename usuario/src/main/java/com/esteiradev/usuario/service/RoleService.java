package com.esteiradev.usuario.service;

import com.esteiradev.usuario.enums.RoleType;
import com.esteiradev.usuario.model.RoleModel;

import java.util.Optional;

public interface RoleService {
    Optional<RoleModel> findByRoleName(RoleType roleType);
}
