package com.ssu.travel.cost.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CostUpdateRequest {
    private Long totalAmount;
    private String title;
    private String content;
    private Map<Long, Long> amountsPerUser;
    private Long payerId;
}
