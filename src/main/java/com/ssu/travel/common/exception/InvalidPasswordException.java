package com.ssu.travel.common.exception;

import com.ssu.travel.common.code.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidPasswordException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
