package com.ssu.travel.global.security.jwt.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtPreAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;

    public JwtPreAuthenticationToken(String token) {
        super(null);
        this.setAuthenticated(false);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
