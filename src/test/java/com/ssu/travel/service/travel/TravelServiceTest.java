package com.ssu.travel.service.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.dto.travel.TravelResponseDto;
import com.ssu.travel.dto.user.SimpleUserInfoDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelRepository;
import com.ssu.travel.jpa.travel.TravelType;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import com.ssu.travel.jpa.user.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
class TravelServiceTest {

    @Autowired
    TravelRepository travelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TravelService travelService;

    @Test
    void createTravel() {
        User user = userRepository.save(new User(UserType.USER, "jmjmjames@ssu.ac.kr", "정종민", "711", 1L));
        TravelCreateRequestDto requestDto = new TravelCreateRequestDto(user.getId(), "졸업 여행", TravelType.PUBLIC,
                LocalDate.now(), LocalDate.of(2022, 12, 25));
        travelService.insertTravel(requestDto.toEntity());

        com.ssu.travel.jpa.travel.Travel travel = travelRepository.findAll().get(0);
        Assertions.assertThat(travel.getTitle()).isEqualTo("졸업 여행");
    }

    @Test
    void addUserTravel() {
        // given
        User userA = userRepository.save(new User(UserType.USER, "jmjmjames@ssu.ac.kr", "정종민", "711", 1L));
        TravelCreateRequestDto requestDto = new TravelCreateRequestDto(userA.getId(), "졸업 여행", TravelType.PUBLIC,
                LocalDate.now(), LocalDate.of(2022, 12, 25));

        Travel travel = travelService.insertTravel(requestDto.toEntity());

        // when
        User userB = userRepository.save(new User(UserType.USER, "pkb9239@ssu.ac.kr", "박경빈", "0711", 2L));
        travelService.addUserToTravel(travel.getId(), userB.getId());

        List<SimpleUserInfoDto> users = travelService.getSimpleUsersOfTravel(travel.getId());
        Assertions.assertThat(users).hasSize(2);

        users.forEach(u -> System.out.println(u.toString()));

        travelService.insertTravel(requestDto.toEntity());

        travelRepository.findAll()
                .forEach(t -> System.out.println("t.getEndDate() = " + t.getEndDate()));
    }
}
