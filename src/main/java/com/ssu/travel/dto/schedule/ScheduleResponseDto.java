package com.ssu.travel.dto.schedule;

import com.ssu.travel.jpa.schedule.Schedule;

import java.time.LocalDateTime;

public class ScheduleResponseDto {
    private final Long travelId;
    private final Long scheduleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ScheduleResponseDto(Schedule entity) {
        this.travelId = entity.getTravel().getId();
        this.scheduleId = entity.getId();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
