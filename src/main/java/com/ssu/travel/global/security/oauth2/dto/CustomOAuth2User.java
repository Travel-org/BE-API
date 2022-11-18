package com.ssu.travel.global.security.oauth2.dto;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.oauth2.model.OAuth2UserInfo;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private final User user;
    private final boolean isFirstLogin;

    public CustomOAuth2User(OAuth2UserInfo oAuth2UserInfo, User user, boolean isFirstLogin) {
        super(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2UserInfo.getAttributes(),
                user.getProvider().getAttributeKey());
        this.user = user;
        this.isFirstLogin = isFirstLogin;
    }

    @Override
    public String getName() {
        return String.valueOf(user.getId());
    }
}
