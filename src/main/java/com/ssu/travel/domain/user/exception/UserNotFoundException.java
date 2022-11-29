package com.ssu.travel.domain.user.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
