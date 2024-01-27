package com.planner.payments.service.RoleService;

import com.planner.payments.domain.Role;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(com.planner.payments.constants.Role role) throws NotFoundException {
        return roleRepository.getRoleByName(role.toString()).orElseThrow(NotFoundException::new);
    }

    @Override
    public Collection<Role> getRoles() {
        return roleRepository.findAll();
    }
}
