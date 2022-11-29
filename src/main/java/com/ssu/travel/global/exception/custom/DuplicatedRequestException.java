package com.ssu.travel.global.exception.custom;

import com.ssu.travel.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedRequestException extends CustomException {

    public DuplicatedRequestException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
