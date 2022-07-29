package com.ssu.travel.jpa.post;

import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.place.PlaceRepository;
import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.jpa.schedule.ScheduleRepository;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelRepository;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import com.ssu.travel.jpa.user.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    TravelRepository travelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PlaceRepository placeRepository;

    Travel travel = Travel.builder()
            .managerId(1L)
            .title("title")
            .memo("memo")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .build();
    Place place = Place.builder()
            .lat(127.127)
            .lng(37.37)
            .placeName("placeName")
            .addressName("addressName")
            .addressRoadName("roadName")
            .placeUrl("urlURL")
            .build();

    Schedule schedule = Schedule.builder()
            .travel(travel)
            .place(place)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now())
            .build();

    User user = User.builder()
            .userType(UserType.USER)
            .email("test@test.com")
            .name("testName")
            .phoneNumber("010-1234-1234")
            .kakaoId(123456789L)
            .build();

    @Test
    @DisplayName("포스트 내용 수정")
    void update() {
        // given
        Post post = Post.builder()
                .schedule(schedule)
                .user(user)
                .title("title1")
                .text("content1")
                .build();


        userRepository.save(user);
        travelRepository.save(travel);
        placeRepository.save(place);
        scheduleRepository.save(schedule);
        postRepository.save(post);

        // when
        String updateTitle = "title2";
        String updateText = "content2";
        post.update(updateTitle, updateText);
        postRepository.save(post);

        Post foundPost = postRepository.findAll().get(0);

        // then
        assertThat(foundPost.getTitle()).isEqualTo(updateTitle);
        assertThat(foundPost.getText()).isEqualTo(updateText);
    }
}
