package com.ssu.travel.domain.friend.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FriendNotFoundException extends CustomException {

    public FriendNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
