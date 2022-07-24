package com.ssu.travel.dto.user;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserSimpleInfoDto {
    private final Long userId;
    private final String userName;

    public UserSimpleInfoDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
