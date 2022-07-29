package com.ssu.travel.jpa.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    GUEST("ROLE_GUEST", "guest"),
    USER("ROLE_USER", "user"),
    HOST("ROLE_HOST", "host"),
    ADMIN("ROLE_ADMIN", "administrator"),
    LOCAL_GOVERNMENT("ROLE_LOCAL_GOVERNMENT", "local government");

    private final String key;
    private final String title;
}
