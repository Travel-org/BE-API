package com.ssu.travel.security.oauth2.dto.requset;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmailPasswordRequest {

    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
