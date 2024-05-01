package com.esteiradev.usuario.service.impl;

import com.esteiradev.usuario.enums.RoleType;
import com.esteiradev.usuario.model.RoleModel;
import com.esteiradev.usuario.repositories.RoleRepository;
import com.esteiradev.usuario.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
