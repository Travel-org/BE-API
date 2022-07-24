package com.ssu.travel.dto.user;

import com.ssu.travel.jpa.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseInfoDto {
    private final Long userId;
    private final String userType;
    private final String name;
    private final String email;
    private final String phoneNumber;

    public UserResponseInfoDto(User entity) {
        this.userId = entity.getId();
        this.userType = entity.getUserType().toString();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.phoneNumber = entity.getPhoneNumber();
    }
}
