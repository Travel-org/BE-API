package com.ssu.travel.global.security.oauth2.service;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.repository.UserRepository;
import com.ssu.travel.global.security.jwt.JwtProperties;
import com.ssu.travel.global.security.jwt.service.JwtService;
import com.ssu.travel.global.security.jwt.service.RefreshTokenService;
import com.ssu.travel.global.security.oauth2.dto.CustomOAuth2User;
import com.ssu.travel.global.security.oauth2.model.OAuth2Provider;
import com.ssu.travel.global.security.oauth2.model.OAuth2UserInfo;
import com.ssu.travel.global.security.oauth2.model.OAuth2UserInfoFactory;
import com.ssu.travel.global.security.utils.ClientIpUtils;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final JwtService<User> jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;

    @Value("${redirect.url}")
    private String redirectUrl;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();
        OAuth2Provider provider = OAuth2Provider.valueOf(registrationId.toUpperCase(Locale.ROOT));

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.createUserInfo(provider, attributes);

        Optional<User> findUser = userRepository.findByEmailAndProvider(userInfo.getEmail(),
                userInfo.getOAuth2Provider());

        User user;
        boolean isFirstLogin;
        if (findUser.isEmpty()) {
            user = User.of(userInfo.getEmail(), userInfo.getGender(), userInfo.getBirthday(),
                    userInfo.getOAuth2Provider());
            userRepository.save(user);
            isFirstLogin = true;
        } else {
            user = findUser.get();
            isFirstLogin = false;
        }
        return new CustomOAuth2User(userInfo, user, isFirstLogin);
    }

    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        User loginUser = oauth2User.getUser();
        boolean isFirstLogin = oauth2User.isFirstLogin();

        // access Token - expTime unit is minute
        String accessToken = jwtService.createToken(jwtProperties.getAccessTokenSigningKey(),
                jwtProperties.getAccessTokenExpirationTime(),
                ChronoUnit.MINUTES,
                createUserPayload(loginUser)
        );
        // refresh Token - expTime unit is days
        String refreshToken = jwtService.createToken(jwtProperties.getRefreshTokenSigningKey(),
                jwtProperties.getRefreshTokenExpirationTime(),
                ChronoUnit.DAYS,
                null
        );

        // 리프레쉬 토큰 DB 저장 (저장시 사용자의 접속 기기 정보를 고려함)
        String clientIp = ClientIpUtils.getClientIP(request);
        String userAgent = request.getHeader("User-Agent");
        refreshTokenService.saveRefreshToken(loginUser, refreshToken, clientIp, userAgent);

        String redirectUri = UriComponentsBuilder
                .fromUriString(redirectUrl)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .queryParam("is_first", isFirstLogin)
                .toUriString();

        response.sendRedirect(redirectUri);
    }

    private Map<String, Object> createUserPayload(User user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", user.getId());
        payload.put("email", user.getEmail());
        payload.put("role", user.getRole());
        return payload;
    }
}
