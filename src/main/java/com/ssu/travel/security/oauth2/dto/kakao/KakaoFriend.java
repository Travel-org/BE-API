package com.ssu.travel.security.oauth2.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoFriend {
    private Long id;
    private String uuid;
    private Boolean favorite;
    private String profile_nickname;
    private String profile_thumbnail_image;
}
