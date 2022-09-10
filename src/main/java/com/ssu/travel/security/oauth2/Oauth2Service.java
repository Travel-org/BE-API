package com.ssu.travel.security.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.security.dto.AuthorizationKakao;
import com.ssu.travel.security.jwt.provider.JwtTokenProvider;
import com.ssu.travel.user.User;
import com.ssu.travel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class Oauth2Service {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final JwtTokenProvider jwtTokenProvider;
    @Value("${auth.kakaoOauth2ClinetId}")
    private String kakaoOauth2ClinetId;
    @Value("${auth.frontendRedirectUrl}")
    private String frontendRedirectUrl;

    public AuthorizationKakao callTokenApi(String origin, String code) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String grantType = "authorization_code";
        params.add("grant_type", grantType);
        params.add("client_id", kakaoOauth2ClinetId);
        params.add("redirect_uri", origin + "oauth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        log.info("request.getBody() = {}", request.getBody());

        String url = "https://kauth.kakao.com/oauth/token";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("response.getBody() = {}", response.getBody());

            return objectMapper.readValue(response.getBody(), AuthorizationKakao.class);
        } catch (RestClientException | JsonProcessingException ex) {
            log.warn("RestClientException | JsonProcessingException : {}", ex.getMessage());
            throw new RestClientException("error");
        }
    }

    public JSONObject callGetUserByAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            SecurityContext context = SecurityContextHolder.getContext();

            log.info("response: {}", response.getBody());

            return stringToJson(response.getBody());
        } catch (RestClientException | ParseException ex) {
            log.warn("RestClientException | ParseException : {}", ex.getMessage());
            throw new RestClientException("error");
        }
    }

    public JSONObject setSessionOrRedirectToSignUp(JSONObject userInfoFromKakao, String accessToken) {
        Long kakaoId = (Long) userInfoFromKakao.get("id");
        JSONObject kakao_account = (JSONObject) userInfoFromKakao.get("kakao_account");

        Optional<User> user = userRepository.findByKakaoId(kakaoId);

        JSONObject result = new JSONObject();

        if (kakao_account.get("email") != null) {
            if (user.isEmpty()) {
                result.put("status", 301);
                result.put("kakaoId", kakaoId);
                return result;
            } else {
                User authUser = user.get();
                String token = jwtTokenProvider.createToken(authUser.getId(), accessToken);
                result.put("status", 200);
                result.put("token", token);
            }
        } else {
            result.put("status", 401);
        }

        return result;
    }


    public JSONObject stringToJson (String userInfo) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(userInfo);
        return (JSONObject) object;
    }

}
