package com.ssu.travel.domain.cost.web.request;

import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class CreateCostRequest {

    @NotNull(message = "총액을 입력해주세요.")
    private Long totalAmount;

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    private String content;

    @NotNull(message = "사용자 별 금액에 대한 정보가 필요합니다.")
    private Map<Long, Long> amountsPerUser;

    @NotNull(message = "결제자의 아이디가 필요합니다.")
    private Long payerId;
}
