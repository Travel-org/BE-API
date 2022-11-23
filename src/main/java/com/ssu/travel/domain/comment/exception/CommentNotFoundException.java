package com.ssu.travel.domain.comment.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

