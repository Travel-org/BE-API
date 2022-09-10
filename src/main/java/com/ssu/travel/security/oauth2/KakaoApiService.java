package com.ssu.travel.security.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.security.oauth2.dto.kakao.KakaoFriendsResponse;
import com.ssu.travel.security.oauth2.dto.kakao.KakaoMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";


    public KakaoMessageResponse sendTextMessage(String[] receiverUuids, String templateObject, String accessToken) {
        String url = "https://kapi.kakao.com/v1/api/talk/friends/message/default/send";
        HttpHeaders headers = new HttpHeaders();
        String token = BEARER_PREFIX + accessToken;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION_HEADER, token);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("receiver_uuids", receiverUuids);
        params.add("template_object", templateObject);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return objectMapper.readValue(response.getBody(), KakaoMessageResponse.class);
        } catch(RestClientException | JsonProcessingException ex) {
            log.info("sendTextMessage Error {}", ex.getMessage());
            throw new RestClientException("error");
        }
    }

    public KakaoFriendsResponse getKakaoFriends(String accessToken) {
        String url = "https://kapi.kakao.com/v1/api/talk/friends";

        HttpHeaders headers = new HttpHeaders();

        String token = BEARER_PREFIX + accessToken;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION_HEADER, token);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return objectMapper.readValue(response.getBody(), KakaoFriendsResponse.class);
        } catch(RestClientException | JsonProcessingException ex) {
            log.info("getKakaoFriends Error {}", ex.getMessage());
            throw new RestClientException("error");
        }
    }

}
