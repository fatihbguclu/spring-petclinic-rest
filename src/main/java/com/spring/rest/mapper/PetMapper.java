package com.spring.rest.mapper;

import com.spring.rest.model.Pet;
import com.spring.rest.model.PetType;
import com.spring.rest.controller.dto.PetDto;
import com.spring.rest.controller.dto.PetFieldsDto;
import com.spring.rest.controller.dto.PetTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface PetMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    PetDto toPetDto(Pet pet);

    Collection<PetDto> toPetsDto(Collection<Pet> pets);

    Collection<Pet> toPets(Collection<PetDto> pets);

    Pet toPet(PetDto petDto);

    Pet toPet(PetFieldsDto petFieldsDto);

    PetTypeDto toPetTypeDto(PetType petType);

    PetType toPetType(PetTypeDto petTypeDto);

    Collection<PetTypeDto> toPetTypeDtos(Collection<PetType> petTypes);

}
