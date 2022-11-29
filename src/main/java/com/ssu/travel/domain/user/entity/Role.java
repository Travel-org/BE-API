package com.ssu.travel.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Role {
    ROLE_USER,
    ROLE_HOST,
    ROLE_ADMIN,
    ROLE_LOCAL_GOVERNMENT;
}
