package com.ssu.travel.security.oauth2.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoMessageResponse {
    private String[] successful_receiver_uuids;
    private KakaoMessageFailureInfo[] failure_info;
}
