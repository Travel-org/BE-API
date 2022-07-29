package com.ssu.travel.exception.custom;

import com.ssu.travel.exception.ErrorCode;
import lombok.Getter;

@Getter
public class RecordNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
