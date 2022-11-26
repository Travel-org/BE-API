package com.ssu.travel.domain.schedule.service;

//import com.ssu.travel.aws.AwsS3Service;

import static java.util.stream.Collectors.*;

import com.ssu.travel.domain.branch.entity.Branch;
import com.ssu.travel.domain.branch.exception.BranchNotFoundException;
import com.ssu.travel.domain.branch.repository.BranchRepository;
import com.ssu.travel.domain.place.entity.Place;
import com.ssu.travel.domain.place.service.PlaceService;
import com.ssu.travel.domain.schedule.exception.ScheduleNotFoundException;
import com.ssu.travel.domain.schedule.web.request.CreateScheduleRequest;
import com.ssu.travel.domain.schedule.dto.ScheduleDto;
import com.ssu.travel.domain.schedule.repository.ScheduleRepository;
import com.ssu.travel.domain.schedule.entity.Schedule;
//import com.ssu.travel.travel.travelDate.TravelDate;
import com.ssu.travel.domain.schedule.web.request.UpdateScheduleRequest;
import com.ssu.travel.domain.travel.entity.Travel;
import com.ssu.travel.domain.travel.service.TravelService;
import com.ssu.travel.domain.travel.entity.TravelDate;
import com.ssu.travel.domain.travel.service.TravelDateService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.exception.UserNotFoundException;
import com.ssu.travel.domain.user.service.UserService;
import com.ssu.travel.domain.usertravel.entity.UserTravel;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final BranchRepository branchRepository;

    private final TravelService travelService;
    private final TravelDateService travelDateService;
    private final UserService userService;
    private final PlaceService placeService;

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public ScheduleDto saveSchedule(Long travelId, LocalDate date, CreateScheduleRequest request) {
        Travel travel = travelService.findTravelEntityById(travelId);
        TravelDate travelDate = travelDateService.findTravelDateByTravelAndDate(travel, date);
        Place place = placeService.findOrSavePlace(request.getPlace());

        Schedule schedule = scheduleRepository.save(
                Schedule.of(travelDate, place, request.getStartTime(), request.getEndTime()));
        travelDate.getScheduleNaturalOrder().add(schedule.getId());

        request.getUserIds()
                .forEach(userId -> {
                    Branch branch = Branch.of(userService.findUserEntityById(userId), schedule);
                    branchRepository.save(branch);
                    schedule.addUser(branchRepository.save(branch));
                });

        return ScheduleDto.from(schedule);
    }

    @Transactional
    public ScheduleDto updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = findScheduleEntityById(scheduleId);

        Map<Long, User> currUsers = schedule.getTravelDate()
                .getTravel()
                .getUserTravels().stream()
                .collect(toMap(userTravel -> userTravel.getUser().getId(), UserTravel::getUser, (a, b) -> b));

        if (!Objects.equals(schedule.getPlace(), request.getPlace())) {
            Place place = placeService.findOrSavePlace(request.getPlace());
            schedule.setPlace(place);
        }

        if (schedule.getStartTime() != request.getStartTime()) {
            schedule.setStartTime(request.getStartTime());
        }

        if (schedule.getEndTime() != request.getEndTime()) {
            schedule.setEndTime(request.getEndTime());
        }

        List<Long> outUserIds = schedule.getBranches().stream()
                .map(branch -> branch.getUser().getId())
                .collect(toList());

        List<Long> inUserIds = request.getUserIds();
        inUserIds.removeAll(outUserIds);
        outUserIds.removeAll(inUserIds);
        inUserIds.forEach(userId -> {
            User userEntity = userService.findUserEntityById(userId);
            branchRepository.save(Branch.of(userEntity, schedule));
        });

        outUserIds.forEach(userId -> {
            if (!currUsers.containsKey(userId)) {
                throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
            }

            Branch branch = branchRepository.findByUserIdAndScheduleId(userId, scheduleId)
                    .orElseThrow(() -> new BranchNotFoundException("해당 Branch를 찾을 수 없습니다."));
            schedule.removeUser(branch);
            branchRepository.delete(branch);
        });

        em.flush();
        em.clear();

        Schedule updatedSchedule = findScheduleEntityById(scheduleId);
        return ScheduleDto.from(updatedSchedule);
    }

    public ScheduleDto getScheduleById(Long scheduleId) {
        Schedule schedule = findScheduleEntityById(scheduleId);
        return ScheduleDto.from(schedule);
    }

    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleDto::from)
                .collect(toList());
    }

    public List<ScheduleDto> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).stream()
                .map(ScheduleDto::from)
                .collect(toList());
    }

    // == Entity 조회 메서드 ==
    public Schedule findScheduleEntityById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다."));
    }
}
