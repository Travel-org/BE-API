package com.ssu.travel.global.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.jwt.exception.ExpiredJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.ModulatedJwtTokenException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String ACCESS_TOKEN_EXPIRATION = "엑세스 토큰이 만료되었습니다.";
    private static final String TOKEN_MODULATED = "엑세스 토큰이 변조되었습니다.";

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        Result<?> result = null;
        if (exception instanceof ExpiredJwtTokenException) {
            result = Result.createErrorResult(ACCESS_TOKEN_EXPIRATION);
        } else if (exception instanceof ModulatedJwtTokenException) {
            result = Result.createErrorResult(TOKEN_MODULATED);
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if (Objects.nonNull(result)) {
            PrintWriter writer = response.getWriter();
            writer.write(mapper.writeValueAsString(result));
        }
    }
}
