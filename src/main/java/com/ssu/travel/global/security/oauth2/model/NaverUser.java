package com.ssu.travel.global.security.oauth2.model;

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
    public OAuth2Provider getOAuth2Provider() {
        return OAuth2Provider.NAVER;
    }
}
