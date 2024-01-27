package com.planner.payments.mapper.person;

import com.planner.payments.DTO.PersonDTO;
import com.planner.payments.mapper.CycleReferencesResolver;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class PersonCycleReferencesResolver extends CycleReferencesResolver {
//    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();
//
//    @BeforeMapping
//    public <T extends PersonDTO> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
//        return (T) knownInstances.get( source );
//    }
//
//    @BeforeMapping
//    public void storeMappedInstance(Object source, @MappingTarget PersonDTO target) {
//        knownInstances.put( source, target );
//    }
}
