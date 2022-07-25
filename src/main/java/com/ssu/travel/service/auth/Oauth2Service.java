package com.ssu.travel.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.config.auth.CustomAuthentication;
import com.ssu.travel.dto.AuthorizationKakao;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${auth.kakaoOauth2ClinetId}")
    private String kakaoOauth2ClinetId;
    @Value("${auth.frontendRedirectUrl}")
    private String frontendRedirectUrl;

    public AuthorizationKakao callTokenApi(String code) {
        String grantType = "authorization_code";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", kakaoOauth2ClinetId);
        params.add("redirect_uri", frontendRedirectUrl + "oauth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        System.out.println("request.getBody() = " + request.getBody());
        String url = "https://kauth.kakao.com/oauth/token";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("response.getBody() = " + response.getBody());
            System.out.println("response.getHeaders() = " + response.getHeaders());

            return objectMapper.readValue(response.getBody(), AuthorizationKakao.class);
        } catch (RestClientException | JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RestClientException("error");
        }
    }

    public JSONObject callGetUserByAccessToken(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//            SecurityContext context = SecurityContextHolder.getContext();
            JSONObject userInfo = stringToJson(response.getBody());
            System.out.println("userInfo.get(\"id\") = " + userInfo.get("id"));
            JSONObject properties = (JSONObject) userInfo.get("properties");
            System.out.println("properties.get(\"nickname\") = " + properties.get("nickname"));
            Long kakaoId = (Long) userInfo.get("id");
//            context.setAuthentication(new CustomAuthentication(kakaoId));
            return userInfo;
        } catch (RestClientException | ParseException ex) {
            ex.printStackTrace();
            throw new RestClientException("error");
        }
    }

    public JSONObject setSessionOrRedirectToSignUp(Long kakaoId) {
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        JSONObject result = new JSONObject();
        if (user.isEmpty()) {
            result.put("status", 301);
            result.put("kakaoId", kakaoId);
            return result;
        } else {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(new CustomAuthentication(user.get()));
            result.put("status", 200);
        }
        return result;
    }

    public JSONObject stringToJson(String userInfo) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(userInfo);
        return (JSONObject) object;
    }
}
