package com.ssu.travel.dto.user;

import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserType;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    private String userType;
    private String name;
    private String email;
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .userType(UserType.valueOf(this.userType))
                .name(this.name)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
