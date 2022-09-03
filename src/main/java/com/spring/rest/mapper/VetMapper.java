package com.spring.rest.mapper;

import com.spring.rest.dto.VetDto;
import com.spring.rest.model.Vet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface VetMapper {

    Vet toVet(VetDto vetDto);

    VetDto toVetDto(Vet vet);

}
