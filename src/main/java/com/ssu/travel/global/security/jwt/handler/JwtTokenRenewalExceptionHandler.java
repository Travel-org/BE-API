package com.ssu.travel.global.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.jwt.exception.ExpiredJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.ModulatedJwtTokenException;
import com.ssu.travel.global.security.jwt.exception.NotFoundRefreshTokenException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenRenewalExceptionHandler {
    private static final String TOKEN_NOT_FOUND = "JWT 인증 토큰이 누락되었습니다.";
    private static final String REFRESH_TOKEN_EXCEPTION = "잘못된 리프레시 토큰입니다.";

    private final ObjectMapper objectMapper;

    public void onRenewalFailure(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws IOException {
        Result result = null;
        if (e instanceof NullPointerException) {
            result = Result.createErrorResult(TOKEN_NOT_FOUND);
        } else if (e instanceof ModulatedJwtTokenException) {
            result = Result.createErrorResult(REFRESH_TOKEN_EXCEPTION);
        } else if (e instanceof ExpiredJwtTokenException) {
            result = Result.createErrorResult(REFRESH_TOKEN_EXCEPTION);
        } else if (e instanceof NotFoundRefreshTokenException) {
            result = Result.createErrorResult(REFRESH_TOKEN_EXCEPTION);
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setHeader(HttpHeaders.CONTENT_ENCODING, "UTF-8");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        if (Objects.nonNull(result)) {
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(result));
        }
    }
}
