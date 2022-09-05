package com.spring.rest.dto;


import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String username;

    private String password;

    private Boolean enabled;

    private List<RoleDto> roles = null;
}
