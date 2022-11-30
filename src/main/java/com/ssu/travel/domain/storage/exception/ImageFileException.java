package com.ssu.travel.domain.storage.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ImageFileException extends CustomException {

    public ImageFileException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
