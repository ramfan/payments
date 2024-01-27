package com.planner.payments.mapper.credit;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.mapper.CycleReferencesResolver;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class CreditCycleReferencesResolver extends CycleReferencesResolver {
//    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();
//
//    @BeforeMapping
//    public <T extends CreditDTO> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
//        return (T) knownInstances.get( source );
//    }
//
//    @BeforeMapping
//    public void storeMappedInstance(Object source, @MappingTarget CreditDTO target) {
//        knownInstances.put( source, target );
//    }
}
