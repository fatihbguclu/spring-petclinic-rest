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
public class OwnerDto {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private Integer id;
    private List<PetDto> pets = new ArrayList<>();
}
