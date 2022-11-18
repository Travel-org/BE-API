package com.ssu.travel.global.security.oauth2.model;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo createUserInfo(OAuth2Provider provider, Map<String, Object> attributes) {
        OAuth2UserInfo userInfo = null;
        switch (provider) {
            case KAKAO:
                userInfo = new KakaoUser(attributes);
                break;
            case NAVER:
                userInfo = new NaverUser(attributes);
                break;
            case GOOGLE:
                userInfo = new GoogleUser(attributes);
                break;
        }
        return userInfo;
    }
}
