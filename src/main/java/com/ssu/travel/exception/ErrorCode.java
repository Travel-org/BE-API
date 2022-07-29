package com.ssu.travel.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(400, "USER RECORD IS NOT FOUND"),
    BRANCH_NOT_FOUND(400, "BRANCH RECORD IS NOT FOUND"),
    COST_NOT_FOUND(400, "COST RECORD IS NOT FOUND"),
    PHOTO_NOT_FOUND(400, "PHOTO RECORD IS NOT FOUND"),
    PLACE_NOT_FOUND(400, "PLACE RECORD IS NOT FOUND"),
    POST_NOT_FOUND(400, "POST RECORD IS NOT FOUND"),
    SCHEDULE_NOT_FOUND(400, "SCHEDULE RECORD IS NOT FOUND"),
    TRAVEL_NOT_FOUND(400, "TRAVEL RECORD IS NOT FOUND"),
    API_NOT_FOUND(404, "API IS NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR")
    ;

    private final int status;
    private final String message;
}
