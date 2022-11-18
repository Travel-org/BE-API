package com.ssu.travel.global.security.jwt.matcher;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class LogoutRequestMatcher implements RequestMatcher {
    private final AntPathRequestMatcher matcher;

    public LogoutRequestMatcher() {
        matcher = new AntPathRequestMatcher("/api/logout", HttpMethod.DELETE.name());
    }


    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher.matches(request);
    }
}
