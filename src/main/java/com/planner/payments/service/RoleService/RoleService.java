package com.planner.payments.service.RoleService;

import com.planner.payments.domain.Role;
import com.planner.payments.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface RoleService {
    Role getRoleByName(com.planner.payments.constants.Role role) throws NotFoundException;
    Collection<Role> getRoles();
}
