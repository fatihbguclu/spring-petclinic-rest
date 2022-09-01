package com.spring.rest.mapper;

import com.spring.rest.dto.PetTypeDto;
import com.spring.rest.model.PetType;
import org.mapstruct.Mapper;

@Mapper
public interface PetTypeMapper {

    PetType toPetType(PetTypeDto petTypeDto);

    PetTypeDto toPetTypeDto(PetType petType);


}
