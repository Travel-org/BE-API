package com.ssu.travel.service.auth;

import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import com.ssu.travel.jpa.user.UserType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        properties = {
                "auth.kakaoOauth2ClinetId=testClientId",
                "auth.frontendRedirectUrl=testUrl",
        }
)
class Oauth2ServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Oauth2Service oauth2Service;

    @Value("${auth.kakaoOauth2ClinetId}")
    public String kakaoOauth2ClientId;

    @Value("${auth.frontendRedirectUrl}")
    public String frontendRedirectUrl;

    @Test
    @DisplayName("kakaoId를 통해 유저 회원가입 여부 확인")
    void setSessionOrRedirectToSignUp() {
        Long kakaoId = 1234L;

        User user = User.builder()
                .kakaoId(kakaoId)
                .userType(UserType.USER)
                .email("test@test.com")
                .name("test")
                .phoneNumber("010-1234-1234")
                .build();

        userRepository.save(user);

        JSONObject res1 = oauth2Service.setSessionOrRedirectToSignUp(kakaoId);
        User foundUser = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        JSONObject res2 = oauth2Service.setSessionOrRedirectToSignUp(123L);

        assertThat(res1.get("status")).isEqualTo(200);
        assertThat(res2.get("status")).isEqualTo(301);
        assertThat(foundUser.getKakaoId()).isEqualTo(1234L);
    }
}
