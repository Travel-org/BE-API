package com.ssu.travel.controller.user;

import com.ssu.travel.dto.user.UserCreateRequestDto;
import com.ssu.travel.dto.user.UserResponseInfoDto;
import com.ssu.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseInfoDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public UserResponseInfoDto createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        return userService.createUser(userCreateRequestDto);
    }
}
