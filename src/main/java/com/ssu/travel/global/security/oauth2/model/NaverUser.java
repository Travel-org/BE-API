package com.ssu.travel.global.security.oauth2.model;

import com.ssu.travel.domain.user.entity.Gender;
import java.time.LocalDate;
import java.util.Map;

public class NaverUser extends OAuth2UserInfo {
    private final Map<String, Object> naverAccountAttributes;

    protected NaverUser(Map<String, Object> attributes) {
        super(attributes);
        naverAccountAttributes = (Map<String, Object>) attributes.get(OAuth2Provider.NAVER.getAttributeKey());
    }

    @Override
    public String getEmail() {
        return (String) naverAccountAttributes.get("email");
    }

    @Override
    public Gender getGender() {
        String gender = (String) naverAccountAttributes.get("gender");
        if (gender.equals("M")) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }

    @Override
    public LocalDate getBirthday() {
        String birthYear = (String) naverAccountAttributes.get("birthyear");
        String birthday = (String) naverAccountAttributes.get("birthday");
        String[] monthAndDay = birthday.split("-");
        int month = Integer.parseInt(monthAndDay[0]);
        int day = Integer.parseInt(monthAndDay[1]);
        return LocalDate.of(Integer.parseInt(birthYear), month, day);
    }


    @Override
    public OAuth2Provider getOAuth2Provider() {
        return OAuth2Provider.NAVER;
    }
}
