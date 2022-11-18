package com.ssu.travel.global.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class ModulatedJwtTokenException extends AuthenticationException {

    public ModulatedJwtTokenException(String msg) {
        super(msg);
    }
}
