package com.ssu.travel.service.schedule;

import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.place.PlaceRepository;
import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.jpa.schedule.ScheduleRepository;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final TravelRepository travelRepository;

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public Schedule insertSchedule(Long travelId, Long placeId, LocalDateTime startTime, LocalDateTime endTime) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new RuntimeException("해당 travel이 존재하지 않습니다."));
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new RuntimeException("해당 place가 존재하지 않습니다."));

        return scheduleRepository.save(
                Schedule.builder()
                        .travel(travel)
                        .place(place)
                        .startTime(startTime)
                        .endTime(endTime)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 일정이 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByPlaceId(Long placeId) {
        return scheduleRepository.findSchedulesByPlaceId(placeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleWithPlaceById(Long scheduleId) {
        return scheduleRepository.findScheduleByIdWithPlace(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 일정이 존재하지 않습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByTravelId(Long travelId) {
        return scheduleRepository.findSchedulesByTravelId(travelId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getSchedulesByPlaceName(String placeName) {
        return scheduleRepository.findSchedulesByPlaceName(placeName);
    }

    @Override
    public void deleteAllSchedules() {
        scheduleRepository.deleteAll();
    }
}
