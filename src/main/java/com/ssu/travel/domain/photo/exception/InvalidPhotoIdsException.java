package com.ssu.travel.domain.photo.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidPhotoIdsException extends CustomException {

    public InvalidPhotoIdsException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
