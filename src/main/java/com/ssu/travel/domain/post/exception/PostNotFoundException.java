package com.ssu.travel.domain.post.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
