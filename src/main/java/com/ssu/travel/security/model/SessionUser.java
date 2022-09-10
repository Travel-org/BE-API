package com.ssu.travel.security.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionUser {
    private Long userId;
    private String name;
    private String profilePath;
    private String accessToken;

}
