package com.ssu.travel.domain.branch.exception;

import com.ssu.travel.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class BranchNotFoundException extends CustomException {

    public BranchNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
