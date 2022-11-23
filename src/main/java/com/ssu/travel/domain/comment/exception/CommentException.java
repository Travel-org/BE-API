package com.ssu.travel.domain.comment.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentException extends CustomException {
    public CommentException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}

