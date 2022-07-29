package com.ssu.travel.controller.user;

import com.ssu.travel.dto.user.UserCreateRequestDto;
import com.ssu.travel.dto.user.UserResponseDto;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers() {
        return userService.getUsers().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/signup")
    public UserResponseDto createUser(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
        User user = userService.insertUser(userCreateRequestDto.toEntity());
        return new UserResponseDto(user);
    }

}
