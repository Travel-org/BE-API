package com.ssu.travel.global.security.jwt.token;

import com.ssu.travel.global.security.jwt.dto.CustomUserDetails;
import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtPostAuthenticationToken extends AbstractAuthenticationToken {
    private final CustomUserDetails customUserDetails;

    public JwtPostAuthenticationToken(CustomUserDetails customUserDetails) {
        super(List.of(new SimpleGrantedAuthority(customUserDetails.getUser().getRole().name())));
        this.customUserDetails = customUserDetails;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return customUserDetails;
    }
}
