package com.ssu.travel.domain.schedule.web;

import com.ssu.travel.domain.schedule.dto.ScheduleDto;
import com.ssu.travel.domain.schedule.service.ScheduleService;
import com.ssu.travel.domain.schedule.web.request.CreateScheduleRequest;
import com.ssu.travel.domain.schedule.web.request.UpdateScheduleRequest;
import com.ssu.travel.global.dto.Result;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/api/travel/{travelId}/schedule")
    public ResponseEntity saveSchedule(@PathVariable Long travelId,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       @RequestBody @Validated CreateScheduleRequest request) {
        ScheduleDto scheduleDto = scheduleService.saveSchedule(travelId, date, request);
        return ResponseEntity.ok(Result.createSuccessResult(scheduleDto));
    }

    @PutMapping("/api/travel/{travelId}/schedule/{scheduleId}")
    public ResponseEntity updateSchedule(@PathVariable Long travelId,
                                         @PathVariable Long scheduleId,
                                         @RequestBody @Validated UpdateScheduleRequest request) {
        ScheduleDto scheduleDto = scheduleService.updateSchedule(scheduleId, request);
        return ResponseEntity.ok(Result.createSuccessResult(scheduleDto));
    }

    @GetMapping("/api/travel/{travelId}/schedule/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable Long travelId, @PathVariable Long scheduleId) {
        ScheduleDto scheduleDto = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(Result.createSuccessResult(scheduleDto));
    }

}
