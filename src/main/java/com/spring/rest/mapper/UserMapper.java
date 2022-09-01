package com.spring.rest.mapper;

import com.spring.rest.dto.RoleDto;
import com.spring.rest.dto.UserDto;
import com.spring.rest.model.Role;
import com.spring.rest.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    Role toRole(RoleDto roleDto);

    RoleDto toRoleDto(Role role);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

}
