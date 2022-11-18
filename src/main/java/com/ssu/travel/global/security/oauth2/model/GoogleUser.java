package com.ssu.travel.global.security.oauth2.model;

import static com.ssu.travel.global.security.oauth2.model.OAuth2Provider.*;

import com.ssu.travel.domain.user.entity.Gender;
import java.time.LocalDate;
import java.util.Map;

public class GoogleUser extends OAuth2UserInfo {
    protected GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) super.attributes.get("email");
    }

    @Override
    public Gender getGender() {
        String gender = (String) super.attributes.get("gender");
        if (gender.equals("male")) {
            return Gender.MALE;
        }
        return Gender.FEMALE;
    }

    @Override
    public LocalDate getBirthday() {
        return null;
    }

    @Override
    public OAuth2Provider getOAuth2Provider() {
        return GOOGLE;
    }
}
