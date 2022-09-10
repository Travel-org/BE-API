package com.ssu.travel.security.oauth2.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoFriendsResponse {
    private KakaoFriend[] elements;
    private Integer total_count;
    private String before_url;
    private String after_url;
    private Integer favorite_count;
}
