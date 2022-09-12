package com.ssu.travel.cost.dto.response;

import com.ssu.travel.cost.Cost;
import com.ssu.travel.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CostCreateResponse {
    private Long id;
    private Long totalAmount;
    private String title;
    private String content;
    private Long payerId;
    private List<UserCostResponse> userCosts;

    public static CostCreateResponse from(Cost cost, User payer) {
        return CostCreateResponse.builder()
                .id(cost.getId())
                .totalAmount(cost.getTotalAmount())
                .title(cost.getTitle())
                .content(cost.getContent())
                .payerId(payer.getId())
                .userCosts(cost.getUserCosts().stream().map(UserCostResponse::from).collect(Collectors.toList()))
                .build();
    }
}
