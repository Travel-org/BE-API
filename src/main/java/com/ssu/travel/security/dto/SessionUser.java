package com.ssu.travel.security.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SessionUser implements Serializable {
    private Long userId;
    private String name;
    private String profilePath;
    private String accessToken;
}
