package com.ssu.travel.service.schedule;

import com.ssu.travel.jpa.schedule.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

    List<Schedule> getAllSchedules();

    Schedule insertSchedule(Long travelId, Long placeId, LocalDateTime startTime, LocalDateTime endTime);

    Schedule getScheduleWithPlaceById(Long scheduleId);

    Schedule getScheduleById(Long scheduleId);

    List<Schedule> getSchedulesByPlaceId(Long placeId);

    List<Schedule> getSchedulesByPlaceName(String placeName);

    List<Schedule> getSchedulesByTravelId(Long travelId);

    void deleteAllSchedules();
}
