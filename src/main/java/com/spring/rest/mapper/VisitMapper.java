package com.spring.rest.mapper;

import com.spring.rest.model.Visit;
import com.spring.rest.controller.dto.VisitDto;
import com.spring.rest.controller.dto.VisitFieldsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(uses = PetMapper.class)
public interface VisitMapper {
    Visit toVisit(VisitDto visitDto);

    Visit toVisit(VisitFieldsDto visitFieldsDto);

    @Mapping(source = "pet.id", target = "petId")
    VisitDto toVisitDto(Visit visit);

    Collection<VisitDto> toVisitsDto(Collection<Visit> visits);
}
