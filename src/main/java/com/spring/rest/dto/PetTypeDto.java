package com.spring.rest.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTypeDto {
    private String name;

    private Integer id;
}
