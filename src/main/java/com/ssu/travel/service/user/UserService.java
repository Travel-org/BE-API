package com.ssu.travel.service.user;

import com.ssu.travel.dto.user.UserResponseDto;
import com.ssu.travel.jpa.user.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User insertUser(User user);

    void deleteAllUsers();
}
