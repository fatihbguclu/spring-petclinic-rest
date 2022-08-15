package com.spring.rest.mapper;

import com.spring.rest.model.Role;
import com.spring.rest.model.User;
import com.spring.rest.controller.dto.RoleDto;
import com.spring.rest.controller.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface UserMapper {
    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);

    Collection<RoleDto> toRoleDtos(Collection<Role> roles);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

    Collection<Role> toRoles(Collection<RoleDto> roleDtos);
}
