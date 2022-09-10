package com.ssu.travel.travel.dto;

import com.ssu.travel.schedule.Schedule;
import com.ssu.travel.schedule.dto.SimpleScheduleResponseDto;
import com.ssu.travel.travel.Travel;
import com.ssu.travel.user.dto.response.SimpleUserResponse;
import com.ssu.travel.usertravel.UserTravel;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TravelResponseDto {
    private final Long id;
    private final Long managerId;
    private final String title;
    private final String memo;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<SimpleUserResponse> users;
    private List<SimpleScheduleResponseDto> schedules;

    public TravelResponseDto(Travel entity, List<Schedule> schedules) {
        this.id = entity.getId();
        this.managerId = entity.getManagerId();
        this.title = entity.getTitle();
        this.memo = entity.getMemo();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.users = entity
                .getUserTravels()
                .stream()
                .map(UserTravel::getUser)
                .map(SimpleUserResponse::new)
                .collect(Collectors.toList());

        this.schedules = schedules
                .stream()
                .map(SimpleScheduleResponseDto::new)
                .collect(Collectors.toList());
    }
}
