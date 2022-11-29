package com.ssu.travel.domain.user.dto;

import com.ssu.travel.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class SimpleUserDto {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String profileImagePath;

    public static SimpleUserDto from(User user) {
        return SimpleUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImagePath(user.getProfileImagePath())
                .build();
    }
}
