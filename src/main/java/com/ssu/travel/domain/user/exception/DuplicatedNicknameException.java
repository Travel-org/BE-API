package com.ssu.travel.domain.user.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicatedNicknameException extends CustomException {

    public DuplicatedNicknameException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
