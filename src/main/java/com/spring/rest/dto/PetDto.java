package com.spring.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {

    private String name;

    private LocalDate birthDate;

    private PetTypeDto type;

    private Integer id;

    private Integer ownerId;

    private List<VisitDto> visits = new ArrayList<>();
}
