package com.ssu.travel.global.exception;

import com.ssu.travel.domain.jwt.exception.RefreshTokenNotValidException;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.utils.CookieProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final CookieProvider cookieProvider;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customExceptionHandler(CustomException customException) {
        HttpStatus status = customException.getStatus();
        Result errorResult = customException.getErrorResult();

        log.warn("[CustomException] {}, {}", status, errorResult);

        return ResponseEntity.status(status)
                .body(errorResult);
    }

    @ExceptionHandler(RefreshTokenNotValidException.class)
    public ResponseEntity customJwtExceptionHandler(RefreshTokenNotValidException exception) {
        // 쿠키 삭제
        ResponseCookie responseCookie = cookieProvider.removeRefreshTokenCookie();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(exception.getResult());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindExceptionHandler(BindException exception) {
        return getValidationErrorBody(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        return getValidationErrorBody(exception);
    }

    private ResponseEntity getValidationErrorBody(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    builder.append("[");
                    builder.append(fieldError.getField());
                    builder.append("](은)는 ");
                    builder.append(fieldError.getDefaultMessage());
                    builder.append(" 입력된 값: [");
                    builder.append(fieldError.getRejectedValue());
                    builder.append("]");
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Result.createErrorResult(builder.toString()));
    }

}
