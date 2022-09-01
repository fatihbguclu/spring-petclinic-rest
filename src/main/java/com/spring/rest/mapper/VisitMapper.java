package com.spring.rest.mapper;

import com.spring.rest.dto.VisitDto;
import com.spring.rest.model.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(uses = PetMapper.class)
public interface VisitMapper {

    Visit toVisit(VisitDto visitDto);

    @Mapping(source = "pet.id", target = "petId")
    VisitDto toVisitDto(Visit visit);

}
