package com.ssu.travel.domain.cost.dto;

import static java.util.stream.Collectors.toList;

import com.ssu.travel.domain.cost.entity.Cost;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CostDto {

    private final Long id;
    private final Long totalAmount;
    private final String title;
    private final String content;
    private final Long payerId;
    private final List<UserCostDto> userCosts;

    public static CostDto from(Cost cost) {
        return CostDto.builder()
                .id(cost.getId())
                .totalAmount(cost.getTotalAmount())
                .title(cost.getTitle())
                .content(cost.getContent())
                .payerId(cost.getPayerId())
                .userCosts(cost.getUserCosts().stream()
                        .map(UserCostDto::from)
                        .collect(toList()))
                .build();
    }
}
