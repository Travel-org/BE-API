package com.ssu.travel.common.exception;

import com.ssu.travel.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class KakaoNotAuthenticationExcpetion extends RuntimeException {
    private final ErrorCode errorCode;

    public KakaoNotAuthenticationExcpetion(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
