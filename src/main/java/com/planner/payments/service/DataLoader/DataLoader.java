package com.planner.payments.service.DataLoader;

import com.planner.payments.constants.Operation;
import com.planner.payments.constants.Role;
import com.planner.payments.exception.NotFoundException;
import com.planner.payments.repository.OperationRepository;
import com.planner.payments.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final OperationRepository operationRepository;

    public DataLoader(RoleRepository roleRepository, OperationRepository operationRepository) {
        this.roleRepository = roleRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws NotFoundException {
        var userRole = roleRepository.getRoleByName(Role.USER.toString()).orElseThrow(NotFoundException::new);
        var adminRole = roleRepository.getRoleByName(Role.ADMIN.toString()).orElseThrow(NotFoundException::new);

        if(userRole.getAllowedOperations().isEmpty()){
            initOperationsForUser(userRole);
        }

        if(adminRole.getAllowedOperations().isEmpty()){
            initOperationsForAdmin(adminRole);
        }
    }

    public void initOperationsForUser(com.planner.payments.domain.Role role) throws NotFoundException {
        var read = operationRepository.getOperationByName(Operation.READ.toString()).orElseThrow(NotFoundException::new);
        var write = operationRepository.getOperationByName(Operation.WRITE.toString()).orElseThrow(NotFoundException::new);

        role.addAllowedOperation(read);
        role.addAllowedOperation(write);

        roleRepository.save(role);
    }

    public void initOperationsForAdmin(com.planner.payments.domain.Role role) throws NotFoundException {
        var read = operationRepository.getOperationByName(Operation.READ.toString()).orElseThrow(NotFoundException::new);
        var write = operationRepository.getOperationByName(Operation.WRITE.toString()).orElseThrow(NotFoundException::new);
        var userManaging = operationRepository.getOperationByName(Operation.USER_MANAGING.toString()).orElseThrow(NotFoundException::new);

        role.addAllowedOperation(read);
        role.addAllowedOperation(write);
        role.addAllowedOperation(userManaging);

        roleRepository.save(role);
    }
}
