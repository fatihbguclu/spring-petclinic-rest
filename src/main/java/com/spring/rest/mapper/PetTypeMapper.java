package com.spring.rest.mapper;

import com.spring.rest.model.PetType;
import com.spring.rest.controller.dto.PetTypeDto;
import com.spring.rest.controller.dto.PetTypeFieldsDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface PetTypeMapper {

    PetType toPetType(PetTypeDto petTypeDto);

    PetType toPetType(PetTypeFieldsDto petTypeFieldsDto);

    PetTypeDto toPetTypeDto(PetType petType);

    List<PetTypeDto> toPetTypeDtos(Collection<PetType> petTypes);

}
