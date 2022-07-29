package com.ssu.travel.controller.schedule;

import com.ssu.travel.dto.schedule.ScheduleCreateRequestDto;
import com.ssu.travel.dto.schedule.ScheduleResponseDto;
import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long scheduleId) {
        return new ScheduleResponseDto(scheduleService.getScheduleWithPlaceById(scheduleId));
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleCreateRequestDto scheduleCreateRequestDto) {
        Long travelId = scheduleCreateRequestDto.getTravelId();
        Long placeId = scheduleCreateRequestDto.getPlaceId();
        LocalDateTime startTime = scheduleCreateRequestDto.getStartTime();
        LocalDateTime endTime = scheduleCreateRequestDto.getEndTime();

        Schedule schedule = scheduleService.insertSchedule(travelId, placeId, startTime, endTime);
        return new ScheduleResponseDto(schedule);
    }
}
