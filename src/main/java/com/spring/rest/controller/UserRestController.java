package com.spring.rest.controller;

import com.spring.rest.dto.UserDto;
import com.spring.rest.mapper.UserMapper;
import com.spring.rest.model.User;
import com.spring.rest.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserRestController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        User user = userMapper.toUser(userDto);
        this.userService.saveUser(user);
        return new ResponseEntity<>(userMapper.toUserDto(user), headers, HttpStatus.CREATED);
    }

}
