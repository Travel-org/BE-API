package com.ssu.travel.dto.user;

import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserType;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserCreateRequestDto {
    @NotNull(message = "유저 타입이 필요합니다.")
    private String userType;
    @NotNull(message = "이름이 필요합니다.")
    private String name;
    @NotNull(message = "이메일이 필요합니다.")
    private String email;
    @NotNull(message = "전화번호가 필요합니다.")
    private String phoneNumber;
    @NotNull(message = "카카오 아이디가 필요합니다.")
    private Long kakaoId;

    public User toEntity() {
        return User.builder()
                .userType(UserType.valueOf(userType))
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .kakaoId(kakaoId)
                .build();
    }
}
