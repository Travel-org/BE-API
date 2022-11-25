package com.ssu.travel.domain.cost.dto;

import com.ssu.travel.domain.user.dto.SimpleUserDto;
import com.ssu.travel.domain.usercost.entity.UserCost;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCostDto {

    private final Long userCostId;
    private final Long amount;
    private final SimpleUserDto simpleUserDto;

    public static UserCostDto from(UserCost userCost) {
        return UserCostDto.builder()
                .userCostId(userCost.getId())
                .amount(userCost.getAmount())
                .simpleUserDto(SimpleUserDto.from(userCost.getUser()))
                .build();
    }
}
