package com.ssu.travel.global.security.oauth2.model;

import com.ssu.travel.domain.user.entity.Gender;
import java.time.LocalDate;
import java.util.Map;

public class KakaoUser extends OAuth2UserInfo {
    private final Map<String, Object> kakaoAccountAttributes;

    protected KakaoUser(Map<String, Object> attributes) {
        super(attributes);
        kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccountAttributes.get("email");
    }

    @Override
    public Gender getGender() {
        String gender = (String) kakaoAccountAttributes.get("gender");
        if (gender.equals("male")) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }

    @Override
    public LocalDate getBirthday() {
        String birthYear = (String) kakaoAccountAttributes.get("birthyear");
        String birthday = (String) kakaoAccountAttributes.get("birthday");
        int month = Integer.parseInt(birthday.substring(0, 2));
        int day = Integer.parseInt(birthday.substring(2));
        return LocalDate.of(Integer.parseInt(birthYear), month, day);
    }


    @Override
    public OAuth2Provider getOAuth2Provider() {
        return OAuth2Provider.KAKAO;
    }
}
