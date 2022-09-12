package com.ssu.travel.cost.dto.response;

import com.ssu.travel.cost.Cost;
import com.ssu.travel.user.dto.response.SimpleUserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CostResponse {
    private Long costId;
    private Long totalAmount;
    private String title;
    private String content;
    private Long payerId;
    private List<UserCostResponse> userCosts;

    public static CostResponse from(Cost cost) {
        return CostResponse.builder()
                .costId(cost.getId())
                .totalAmount(cost.getTotalAmount())
                .title(cost.getTitle())
                .content(cost.getContent())
                .userCosts(cost.getUserCosts().stream().map(UserCostResponse::from).collect(Collectors.toList()))
                .payerId(cost.getPayerId())
                .build();
    }
}
