package com.spring.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {

    private String name;

    private LocalDate birthDate;

    private PetTypeDto type;

    private Integer id;

    private Integer ownerId;

    private List<VisitDto> visits = new ArrayList<>();
}
