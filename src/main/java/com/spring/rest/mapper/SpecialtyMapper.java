package com.spring.rest.mapper;

import com.spring.rest.model.Specialty;
import com.spring.rest.controller.dto.SpecialtyDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface SpecialtyMapper {
    Specialty toSpecialty(SpecialtyDto specialtyDto);

    SpecialtyDto toSpecialtyDto(Specialty specialty);

    Collection<SpecialtyDto> toSpecialtyDtos(Collection<Specialty> specialties);

    Collection<Specialty> toSpecialtys(Collection<SpecialtyDto> specialties);

}
