package com.ssu.travel.domain.user.dto;

import com.ssu.travel.domain.user.entity.Gender;
import com.ssu.travel.domain.user.entity.Role;
import com.ssu.travel.domain.user.entity.User;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserDto {
    
    private final Long id;
    private final String email;
    private final String nickname;
    private final Gender gender;
    private final LocalDate birthDay;
    private final String profileImagePath;
    private final Role role;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birthDay(user.getBirthday())
                .profileImagePath(user.getProfileImagePath())
                .role(user.getRole())
                .build();
    }
}

