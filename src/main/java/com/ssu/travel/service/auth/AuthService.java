package com.ssu.travel.service.auth;

import com.ssu.travel.dto.AuthorizationKakao;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Oauth2Service oauth2Service;

    public JSONObject kakaoAuthentication(String code) {
        AuthorizationKakao authorizationKakao = oauth2Service.callTokenApi(code);
        JSONObject userInfoFromKakao = oauth2Service.callGetUserByAccessToken(authorizationKakao.getAccess_token());
        Long kakaoId = (Long) userInfoFromKakao.get("id");
        return oauth2Service.setSessionOrRedirectToSignUp(kakaoId);
    }
}
