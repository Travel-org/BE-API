package com.ssu.travel.domain.schedule.dto;

import static java.util.stream.Collectors.toList;

import com.ssu.travel.domain.branch.entity.Branch;
import com.ssu.travel.domain.place.dto.PlaceDto;
import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.schedulePhoto.dto.SchedulePhotoDto;
import com.ssu.travel.domain.traveldate.entity.TravelDate;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleDto {

    private final Long scheduleId;
    private final Long travelId;
    private final LocalDate date;
    private final PlaceDto place;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<SimpleUserDto> users;
    private final List<SchedulePhotoDto> photos;

    public static ScheduleDto from(Schedule schedule) {
        TravelDate travelDate = schedule.getTravelDate();
        return ScheduleDto.builder()
                .scheduleId(schedule.getId())
                .travelId(travelDate.getTravel()
                        .getId()
                )
                .date(travelDate.getDate())
                .place(PlaceDto.from(schedule.getPlace()))
                .users(schedule.getBranches().stream()
                        .map(Branch::getUser)
                        .map(SimpleUserDto::from)
                        .collect(toList())
                )
                .photos(schedule.getPhotos().stream()
                        .map(SchedulePhotoDto::from)
                        .collect(toList())
                )
                .build();
    }
}
