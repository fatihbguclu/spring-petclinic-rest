package com.spring.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
    private LocalDate date;

    private String description;

    private Integer id;

    @JsonProperty("petId")
    private Integer petId;
}
