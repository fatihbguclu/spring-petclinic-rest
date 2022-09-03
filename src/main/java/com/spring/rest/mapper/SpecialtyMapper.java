package com.spring.rest.mapper;

import com.spring.rest.dto.SpecialtyDto;
import com.spring.rest.model.Specialty;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface SpecialtyMapper {

    Specialty toSpecialty(SpecialtyDto specialtyDto);

    SpecialtyDto toSpecialtyDto(Specialty specialty);


}
