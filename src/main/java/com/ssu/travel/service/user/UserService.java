package com.ssu.travel.service.user;

import com.ssu.travel.dto.user.UserCreateRequestDto;
import com.ssu.travel.dto.user.UserResponseInfoDto;

import java.util.List;

public interface UserService {
    UserResponseInfoDto createUser(UserCreateRequestDto userCreateRequestDto);

    List<UserResponseInfoDto> getUsers();
}
