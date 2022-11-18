package com.ssu.travel.global.security.jwt.provider;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.jwt.JwtProperties;
import com.ssu.travel.global.security.jwt.service.JwtService;
import com.ssu.travel.global.security.jwt.dto.CustomUserDetails;
import com.ssu.travel.global.security.jwt.exception.ExpiredJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.ModulatedJwtTokenException;
import com.ssu.travel.global.security.jwt.token.JwtPostAuthenticationToken;
import com.ssu.travel.global.security.jwt.token.JwtPreAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtProperties jwtProperties;
    private final JwtService<User> jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        try {
            Claims claims = jwtService.getClaimsFromJwtToken(token, jwtProperties.getAccessTokenSigningKey());
            User verifiedUser = jwtService.convertUserModel(claims);
            CustomUserDetails context = new CustomUserDetails(verifiedUser);

            return new JwtPostAuthenticationToken(context);
        } catch (MalformedJwtException | MissingClaimException ex) {
            throw new ModulatedJwtTokenException("엑세스 토큰이 변조되었습니다.");
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtTokenException("엑세스 토큰이 만료되었습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
