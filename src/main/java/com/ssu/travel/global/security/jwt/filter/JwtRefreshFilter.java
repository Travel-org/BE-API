package com.ssu.travel.global.security.jwt.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.jwt.dto.RenewalToken;
import com.ssu.travel.global.security.jwt.JwtProperties;
import com.ssu.travel.global.security.jwt.service.JwtService;
import com.ssu.travel.global.security.jwt.entity.RefreshToken;
import com.ssu.travel.global.security.jwt.exception.ExpiredJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.ModulatedJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.NotFoundRefreshTokenException;
import com.ssu.travel.global.security.jwt.handler.JwtTokenRenewalExceptionHandler;
import com.ssu.travel.global.security.jwt.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {
    private final RefreshTokenService refreshTokenService;
    private final JwtService<User> jwtService;
    private final JwtProperties jwtProperties;
    private final JwtTokenRenewalExceptionHandler jwtTokenRenewalExceptionHandler;
    private final RequestMatcher requestMatcher;
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            ServletInputStream inputStream = request.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            JsonNode jsonNode = objectMapper.readTree(body);

            try {
                String refreshToken = jsonNode.get("refreshToken").asText();
                Claims claims = jwtService.getClaimsFromJwtToken(refreshToken,
                        jwtProperties.getRefreshTokenSigningKey());

                Date expiration = claims.getExpiration();
                RefreshToken findRefreshToken = refreshTokenService.findByToken(refreshToken);
                String newAccessToken = jwtService.createToken(jwtProperties.getAccessTokenSigningKey(),
                        jwtProperties.getAccessTokenExpirationTime(),
                        ChronoUnit.MINUTES,
                        createUserPayload(findRefreshToken.getUser())
                );
                String newRefreshToken = refreshToken;

                // 리프레쉬 토큰 만료 3일이하일 때, 리프레쉬 토큰도 갱신 처리
                if (jwtService.isRenewalRequired(expiration, 3, ChronoUnit.DAYS)) {
                    newRefreshToken = jwtService.createToken(jwtProperties.getRefreshTokenSigningKey(),
                            jwtProperties.getRefreshTokenExpirationTime(),
                            ChronoUnit.DAYS,
                            null
                    );
                    refreshTokenService.renewRefreshToken(findRefreshToken, newRefreshToken);
                }

                response.setStatus(HttpStatus.OK.value());
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                response.setHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8");

                RenewalToken renewalToken = RenewalToken.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();

                response.getWriter()
                        .write(objectMapper.writeValueAsString(Result.createSuccessResult(renewalToken)));

            } catch (NotFoundRefreshTokenException | NullPointerException e) {
                jwtTokenRenewalExceptionHandler.onRenewalFailure(request, response, e);

            } catch (MalformedJwtException | MissingClaimException ex) {
                jwtTokenRenewalExceptionHandler.onRenewalFailure(request, response,
                        new ModulatedJwtTokenException("변조된 JWT 토큰 입니다."));

            } catch (ExpiredJwtException ex) {
                jwtTokenRenewalExceptionHandler.onRenewalFailure(request, response,
                        new ExpiredJwtTokenException("만료된 JWT 토큰 입니다."));
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private Map<String, Object> createUserPayload(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", user.getId());
        payload.put("email", user.getEmail());
        payload.put("role", user.getRole());
        return payload;
    }
}
