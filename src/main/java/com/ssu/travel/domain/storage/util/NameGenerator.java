package com.ssu.travel.domain.storage.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class NameGenerator {

    /**
     * 중복되지 않는 랜덤한 이름을 생성한다.
     * @return randomName - 생성된 무작위 이름 (중복될 확률 극히 낮음 0.00000000006% 이하)
     */
    public String generateRandomName() {
        return UUID.randomUUID().toString();
    }
}
