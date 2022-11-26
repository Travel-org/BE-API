package com.ssu.travel.domain.schedule.web;

import com.ssu.travel.domain.schedule.dto.ScheduleDto;
import com.ssu.travel.domain.schedule.service.ScheduleService;
import com.ssu.travel.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/api/travel/{travelId}/schedule/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable Long travelId, @PathVariable Long scheduleId) {
        ScheduleDto scheduleDto = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(Result.createSuccessResult(scheduleDto));
    }

}
