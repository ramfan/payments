package com.planner.payments.mapper.role;

import com.planner.payments.DTO.RoleDTO;
import com.planner.payments.constants.Operation;
import com.planner.payments.domain.Role;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class RoleMapper {
    public RoleDTO map(@NotNull Role role){
        var roleDto = new RoleDTO();
        roleDto.setId(role.getId());
        roleDto.setName(role.getAuthority());
        var allowedOperation = role.getAllowedOperations()
                .stream()
                .map(this::map)
                .collect(Collectors.toSet());

        roleDto.setAllowedOperations(allowedOperation);
        return roleDto;
    }

    public Operation map (@NotNull com.planner.payments.domain.Operation operation) {
        return Operation.valueOf(operation.getAuthority());
    }
}
