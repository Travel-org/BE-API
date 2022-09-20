package com.ssu.travel.travel.dto;

import com.ssu.travel.schedule.Schedule;
import com.ssu.travel.schedule.dto.SimpleScheduleResponse;
import com.ssu.travel.travel.Travel;
import com.ssu.travel.user.dto.response.SimpleUserResponse;
import com.ssu.travel.usertravel.UserTravel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TravelResponse {
    private Long id;
    private Long managerId;
    private String title;
    private String memo;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<SimpleUserResponse> users;
    private List<SimpleScheduleResponse> schedules;

//    public TravelResponse(Travel entity, List<Schedule> schedules) {
//        this.id = entity.getId();
//        this.managerId = entity.getManagerId();
//        this.title = entity.getTitle();
//        this.memo = entity.getMemo();
//        this.startDate = entity.getStartDate();
//        this.endDate = entity.getEndDate();
////        this.users = entity
////                .getUserTravels()
////                .stream()
////                .map(UserTravel::getUser)
////                .map(SimpleUserResponse::from)
////                .collect(Collectors.toList());
//
//        this.schedules = schedules
//                .stream()
//                .map(SimpleScheduleResponse::new)
//                .collect(Collectors.toList());
//    }
}
