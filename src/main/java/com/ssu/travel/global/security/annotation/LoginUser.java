package com.ssu.travel.global.security.annotation;

import java.lang.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * Security Context 내의 인증 토큰에서 User 객체를 바로 가져오도록 하는 어노테이션. <br>
 * Security Context 내에 익명 인증 토큰이 있는 경우(로그인이 되지 않을 경우), null이 주입되므로 주의해야한다. <br>
 * 이 어노테이션으로 가져온 User 객체는 jpa 에 영속화되지 않은 상태이다. 단순히 SecurityContext에서 가져온 인스턴스이다. <br>
 * User 객체를 통해 조회한 다음 영속화된 User 객체를 조회해야한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@AuthenticationPrincipal(expression = "#this == 'anonymous' ? null : user")
public @interface LoginUser {
}
