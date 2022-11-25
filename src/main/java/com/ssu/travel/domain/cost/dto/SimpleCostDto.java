package com.ssu.travel.domain.cost.dto;

import com.ssu.travel.domain.cost.entity.Cost;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleCostDto {
    private final Long costId;
    private final Long totalAmount;
    private final String title;
    private final List<Long> userIds;
    private final Long payerId;

    public static SimpleCostDto from(Cost cost) {
        return SimpleCostDto.builder()
                .costId(cost.getId())
                .totalAmount(cost.getTotalAmount())
                .title(cost.getTitle())
                .userIds(cost.getUserCosts().stream().map(userCost -> userCost.getUser().getId())
                        .collect(Collectors.toList()))
                .payerId(cost.getPayerId())
                .build();
    }
}
