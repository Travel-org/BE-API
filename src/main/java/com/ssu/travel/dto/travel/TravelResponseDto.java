package com.ssu.travel.dto.travel;

import com.ssu.travel.dto.user.SimpleUserInfoDto;
import com.ssu.travel.jpa.usertravel.UserTravel;
import com.ssu.travel.jpa.travel.Travel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TravelResponseDto {
    private final Long id;
    private final Long managerId;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String memo;
    private final List<SimpleUserInfoDto> users;

    public TravelResponseDto(Travel entity) {
        this.id = entity.getId();
        this.managerId = entity.getManagerId();
        this.title = entity.getTitle();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.memo = entity.getMemo();
        this.users = new ArrayList<>();

        entity.getUserTravels()
                .stream()
                .map(UserTravel::getUser)
                .map(SimpleUserInfoDto::new)
                .forEach(users::add);
    }
}
