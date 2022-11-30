package com.ssu.travel.domain.storage.util;

import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64Decoder {

    /**
     * base64 로 인코딩된 문자열을 디코딩 하여 바이트 배열을 반환한다.
     *
     * @param encoded base64 로 인코딩된 문자열
     * @return decoded - byte[] 디코딩된 바이트 배열
     */
    public byte[] decode(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }
}
