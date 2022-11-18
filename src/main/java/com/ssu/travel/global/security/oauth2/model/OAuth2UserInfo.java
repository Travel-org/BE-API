package com.ssu.travel.global.security.oauth2.model;

import com.ssu.travel.domain.user.entity.Gender;
import com.ssu.travel.global.security.oauth2.model.OAuth2Provider;
import java.time.LocalDate;
import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getEmail();

    public abstract Gender getGender();

    public abstract LocalDate getBirthday();

    public abstract OAuth2Provider getOAuth2Provider();

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
