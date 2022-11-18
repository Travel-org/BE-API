package com.ssu.travel.global.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class ExpiredJwtTokenException extends AuthenticationException {

    public ExpiredJwtTokenException(String msg) {
        super(msg);
    }
}
