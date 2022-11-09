package com.ssu.travel.global.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Result<T> {

    private Code code;
    private String message;
    private T data;

    @Builder(access = AccessLevel.PRIVATE)
    public Result(Code code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result createErrorResult(String message) {
        return Result.builder()
                .code(Code.ERROR)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> Result createSuccessResult(T data) {
        return Result.builder()
                .code(Code.SUCCESS)
                .message("")
                .data(data)
                .build();
    }
}
