package com.spring.rest.mapper;

import com.spring.rest.dto.PetDto;
import com.spring.rest.dto.PetTypeDto;
import com.spring.rest.model.Pet;
import com.spring.rest.model.PetType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface PetMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    PetDto toPetDto(Pet pet);

    Pet toPet(PetDto petDto);

    PetTypeDto toPetTypeDto(PetType petType);

    PetType toPetType(PetTypeDto petTypeDto);

}
