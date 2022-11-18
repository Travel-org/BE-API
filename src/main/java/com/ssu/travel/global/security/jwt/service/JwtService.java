package com.ssu.travel.global.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService<T> {
    private final Function<Claims, T> convertor;

    public String createToken(String key, long expTime, ChronoUnit timeUnit, Map<String, Object> claims) {
        Instant now = ZonedDateTime.now()
                .toInstant();
        SecretKey secretkey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key.getBytes()));

        return Jwts.builder()
                .setExpiration(Date.from(now.plus(expTime, timeUnit)))
                .addClaims(claims)
                .signWith(secretkey)
                .compact();
    }

    public Claims getClaimsFromJwtToken(String token, String key) {
        SecretKey signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key.getBytes()));
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public T convertUserModel(Claims claims) {
        return convertor.apply(claims);
    }

    public boolean isRenewalRequired(Date expTime, long time, ChronoUnit timeUnit) {
        Instant exp = expTime.toInstant();
        Instant now = Instant.now();
        long diff = now.until(exp, timeUnit);

        return diff < time;
    }
}
