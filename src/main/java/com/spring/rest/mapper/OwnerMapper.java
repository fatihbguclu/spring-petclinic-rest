package com.spring.rest.mapper;

import com.spring.rest.model.Owner;
import org.mapstruct.Mapper;
import com.spring.rest.controller.dto.OwnerDto;
import com.spring.rest.controller.dto.OwnerFieldsDto;


import java.util.Collection;

@Mapper(uses = PetMapper.class)
public interface OwnerMapper {

    OwnerDto toOwnerDto(Owner owner);

    Owner toOwner(OwnerDto ownerDto);

    Owner toOwner(OwnerFieldsDto ownerDto);

    List<OwnerDto> toOwnerDtoCollection(Collection<Owner> ownerCollection);

    Collection<Owner> toOwners(Collection<OwnerDto> ownerDtos);

}
