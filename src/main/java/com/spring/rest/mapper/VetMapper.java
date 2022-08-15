package com.spring.rest.mapper;

import com.spring.rest.model.Vet;
import com.spring.rest.controller.dto.VetDto;
import com.spring.rest.controller.dto.VetFieldsDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface VetMapper {
    Vet toVet(VetDto vetDto);

    Vet toVet(VetFieldsDto vetFieldsDto);

    VetDto toVetDto(Vet vet);

    Collection<VetDto> toVetDtos(Collection<Vet> vets);
}
