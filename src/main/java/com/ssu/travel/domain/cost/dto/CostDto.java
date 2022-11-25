package com.ssu.travel.domain.cost.dto;

import com.ssu.travel.domain.cost.dto.response.UserCostResponse;
import com.ssu.travel.domain.cost.entity.Cost;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CostDto {
    private Long id;
    private Long totalAmount;
    private String title;
    private String content;
    private Long payerId;
    private List<UserCostResponse> userCosts;

    public static CostDto from(Cost cost) {
        return CostDto.builder()
                .id(cost.getId())
                .totalAmount(cost.getTotalAmount())
                .title(cost.getTitle())
                .content(cost.getContent())
                .payerId(cost.getPayerId())
                .userCosts(cost.getUserCosts().stream().map(UserCostResponse::from).collect(Collectors.toList()))
                .build();
    }
}
