package com.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VetDto {
    private String firstName;

    private String lastName;

    private List<SpecialtyDto> specialties = new ArrayList<>();

    private Integer id;
}
