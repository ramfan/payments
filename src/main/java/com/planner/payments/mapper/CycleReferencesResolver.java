package com.planner.payments.mapper;

import com.planner.payments.DTO.CreditDTO;
import com.planner.payments.DTO.PersonDTO;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class CycleReferencesResolver {
    private final Map<Object, Object> creditKnownInstances = new IdentityHashMap<>();
    private final Map<Object, Object> personKnownInstances = new IdentityHashMap<>();


    @BeforeMapping
    public CreditDTO getMappedInstance(CreditDTO source, @TargetType Class<CreditDTO> targetType) {
        return (CreditDTO)creditKnownInstances.get( source );
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget CreditDTO target) {
        creditKnownInstances.put( source, target );
    }


    @BeforeMapping
    public PersonDTO getMappedInstance(PersonDTO source, @TargetType Class<PersonDTO> targetType) {
        return (PersonDTO)personKnownInstances.get( source );
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget PersonDTO target) {
        personKnownInstances.put( source, target );
    }
}
