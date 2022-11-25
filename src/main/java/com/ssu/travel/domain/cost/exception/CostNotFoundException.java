package com.ssu.travel.domain.cost.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CostNotFoundException extends CustomException {

    public CostNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
