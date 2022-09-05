package com.spring.rest.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private Integer id;
    private List<PetDto> pets = new ArrayList<>();
}
