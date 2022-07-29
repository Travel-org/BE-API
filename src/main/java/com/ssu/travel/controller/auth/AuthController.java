package com.ssu.travel.controller.auth;

import com.ssu.travel.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @GetMapping("/v1/oauth2/authorization/kakao")
    public JSONObject login(@RequestParam("code") String code) {
        return authService.kakaoAuthentication(code);
    }

    @GetMapping("/v1/login")
    public Boolean isLogin(@RequestHeader("Cookie") Optional<Object> header) {
        return header.isPresent();
    }
}
