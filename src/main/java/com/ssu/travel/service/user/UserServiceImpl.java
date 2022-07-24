package com.ssu.travel.service.user;

import com.ssu.travel.dto.user.UserCreateRequestDto;
import com.ssu.travel.dto.user.UserResponseInfoDto;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    public UserResponseInfoDto createUser(UserCreateRequestDto userCreateRequestDto) {
        User newUser = userCreateRequestDto.toEntity();
        User user = userRepository.save(newUser);
        return new UserResponseInfoDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseInfoDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResponseInfoDto::new)
                .collect(Collectors.toList());
    }
}
