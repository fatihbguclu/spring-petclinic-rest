package com.spring.rest.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VetDto {
    private String firstName;

    private String lastName;

    private List<SpecialtyDto> specialties = new ArrayList<>();

    private Integer id;
}
