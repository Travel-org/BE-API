package com.ssu.travel.cost.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CostCreateRequest {
    @NotNull(message = "총액을 입력해주세요.")
    private Long totalAmount;

    @NotNull(message = "여행 아이디를 입력해주세요.")
    private Long travelId;

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    private String content;

    @NotNull(message = "일괄 정산 여부가 필요합니다.")
    private Boolean isEquallyDivided;

    @NotNull(message = "사용자 별 금액에 대한 정보가 필요합니다.")
    private Map<Long, Long> amountsPerUser;

    @NotNull(message = "결제자의 아이디가 필요합니다.")
    private Long payerId;
}
