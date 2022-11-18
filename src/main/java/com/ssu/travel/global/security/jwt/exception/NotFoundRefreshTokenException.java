package com.ssu.travel.global.security.jwt.exception;

import com.ssu.travel.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundRefreshTokenException extends CustomException {

    public NotFoundRefreshTokenException(HttpStatus status, String message) {
        super(status, message);
    }
}
