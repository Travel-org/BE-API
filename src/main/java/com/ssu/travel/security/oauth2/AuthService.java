package com.ssu.travel.security.oauth2;

import com.ssu.travel.common.code.ErrorCode;
import com.ssu.travel.common.exception.InvalidPasswordException;
import com.ssu.travel.common.exception.RecordNotFoundException;
import com.ssu.travel.security.dto.AuthorizationKakaoDto;
import com.ssu.travel.security.jwt.provider.JwtTokenProvider;
import com.ssu.travel.security.oauth2.dto.requset.EmailPasswordRequest;
import com.ssu.travel.security.oauth2.dto.response.LoginSuccessResponse;
import com.ssu.travel.user.User;
import com.ssu.travel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final Oauth2Service oauth2Service;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JSONObject kakaoAuthentication(String origin, String code) {
        AuthorizationKakaoDto authorizationKakaoDto = oauth2Service.callTokenApi(origin, code);

        JSONObject userInfoFromKakao = oauth2Service.callGetUserByAccessToken(authorizationKakaoDto.getAccess_token());
        return oauth2Service.setSessionOrRedirectToSignUp(userInfoFromKakao, authorizationKakaoDto.getAccess_token());
    }

    public LoginSuccessResponse login(EmailPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RecordNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다", ErrorCode.USER_NOT_FOUND)
        );

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidPasswordException("잘못된 비밀번호 입니다.", ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(user.getId(), null);
        return new LoginSuccessResponse(token);
    }

    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getDetails();
        return user.getId();
    }
}
