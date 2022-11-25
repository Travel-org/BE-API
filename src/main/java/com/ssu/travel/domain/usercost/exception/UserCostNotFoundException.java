package com.ssu.travel.domain.usercost.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserCostNotFoundException extends CustomException {

    public UserCostNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
