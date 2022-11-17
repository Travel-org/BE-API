package com.ssu.travel.global.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {
    @Value("${token.access-token-signing-key}")
    private String accessTokenSigningKey;

    @Value("${token.refresh-token-signing-key}")
    private String refreshTokenSigningKey;

    @Value("${token.access-token-expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private Long refreshTokenExpirationTime;
}
