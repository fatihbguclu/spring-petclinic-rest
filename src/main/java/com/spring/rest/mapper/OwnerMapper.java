package com.spring.rest.mapper;

import com.spring.rest.dto.OwnerDto;
import com.spring.rest.model.Owner;
import org.mapstruct.Mapper;

@Mapper(uses = PetMapper.class)
public interface OwnerMapper {

    OwnerDto toOwnerDto(Owner owner);

    Owner toOwner(OwnerDto ownerDto);

}
