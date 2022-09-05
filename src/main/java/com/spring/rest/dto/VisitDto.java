package com.spring.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitDto {
    private LocalDate date;

    private String description;

    private Integer id;

    @JsonProperty("petId")
    private Integer petId;
}
