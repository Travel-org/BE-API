package com.ssu.travel.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    GUEST("GUEST", "guest"),
    USER("USER", "user"),
    HOST("HOST", "host"),
    ADMIN("ADMIN", "administrator"),
    LOCAL_GOVERNMENT("LOCAL_GOVERNMENT", "local government");

    private final String key;
    private final String title;
}
