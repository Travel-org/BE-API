package com.ssu.travel.service.schedule;

import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserType;
import com.ssu.travel.jpa.usertravel.UserTravelRepository;
import com.ssu.travel.service.place.PlaceService;
import com.ssu.travel.service.travel.TravelService;
import com.ssu.travel.service.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScheduleServiceTest {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PlaceService placeService;

    @Autowired
    UserService userService;

    @Autowired
    TravelService travelService;

    @Autowired
    UserTravelRepository userTravelRepository;

    @AfterEach
    public void cleanup() {
        scheduleService.deleteAllSchedules();
        userTravelRepository.deleteAll();
        userService.deleteAllUsers();
        placeService.deleteAllPlaces();
        travelService.deleteAllTravels();
    }

    Place ssuUniv;
    Place cauUniv;
    User user;
    Travel travel;

    @BeforeEach
    public void setup() {
        ssuUniv = Place.builder()
                .lat(37.494705526855)
                .lng(126.95994559383)
                .placeUrl("ssu.ac.kr")
                .placeName("숭실대학교")
                .addressName("상도동")
                .addressRoadName("상도로")
                .build();

        cauUniv = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("cau.ac.kr")
                .placeName("중앙대학교")
                .addressName("서울")
                .addressRoadName("흑석로")
                .phoneNumber("02.820.1234")
                .build();

        ssuUniv = placeService.insertPlace(ssuUniv);
        cauUniv = placeService.insertPlace(cauUniv);

        user = userService.insertUser(User.builder()
                .name("test")
                .phoneNumber("010-1234-1234")
                .userType(UserType.USER)
                .email("test@test")
                .kakaoId(1L)
                .build());

        travel = travelService.insertTravel(Travel.builder()
                .managerId(user.getId())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .title("첫 여행")
                .build());
    }

    @Test
    @DisplayName("Schedule_DB_삽입_테스트")
    public void testCreateSchedule() {
        Schedule schedule = Schedule.builder()
                .travel(travel)
                .place(ssuUniv)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(1))
                .build();

        scheduleService.insertSchedule(schedule.getTravel().getId(), schedule.getPlace().getId(),
                schedule.getStartTime(), schedule.getEndTime());


        Schedule scheduleWithPlaceById = scheduleService.getScheduleWithPlaceById(schedule.getPlace().getId());

        assertThat(scheduleWithPlaceById.getPlace().getId(), is(ssuUniv.getId()));
        assertThat(scheduleWithPlaceById.getTravel().getId(), is(travel.getId()));
    }

    @Test
    @DisplayName("여행의 schedule들을 불러올 수 있다.")
    public void testGetSchedulesByTravel() {
        Schedule schedule1 = Schedule.builder().travel(travel).place(ssuUniv).startTime(LocalDateTime.now()).endTime(LocalDateTime.now().plusDays(1)).build();
        Schedule schedule2 = Schedule.builder().travel(travel).place(cauUniv).startTime(LocalDateTime.now().plusDays(1)).endTime(LocalDateTime.now().plusDays(3)).build();
        scheduleService.insertSchedule(schedule1.getTravel().getId(), schedule1.getPlace().getId(), schedule1.getStartTime(), schedule1.getEndTime());
        scheduleService.insertSchedule(schedule2.getTravel().getId(), schedule2.getPlace().getId(), schedule2.getStartTime(), schedule2.getEndTime());


        List<Schedule> schedules = scheduleService.getSchedulesByTravelId(travel.getId());

        assertThat(schedules, hasSize(2));
    }
}
