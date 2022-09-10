package com.ssu.travel.security.oauth2.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoMessageFailureInfo {
    private Integer code;
    private String msg;
    private String[] receiver_uuids;
}
