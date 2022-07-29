package com.ssu.travel.service.place;

import com.ssu.travel.dto.place.PlaceResponseDto;
import com.ssu.travel.jpa.place.Place;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class PlaceServiceTest {
    @Autowired
    PlaceService placeService;

    @AfterEach
    public void cleanup() {
        placeService.deleteAllPlaces();
    }

    @Test
    public void insertTest() {
        Place ssuUniv = Place.builder()
                .lat(37.494705526855)
                .lng(126.95994559383)
                .placeUrl("ssu.ac.kr")
                .placeName("숭실대학교")
                .addressName("상도동")
                .addressRoadName("상도로")
                .build();

        Place cauUniv = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("cau.ac.kr")
                .placeName("중앙대학교")
                .addressName("서울")
                .addressRoadName("흑석로")
                .phoneNumber("02.820.1234")
                .build();

        placeService.insertPlace(ssuUniv);
        placeService.insertPlace(cauUniv);
        List<Place> places = placeService.getAllPlaces();

        places.forEach(s -> System.out.printf("{%f, %f} %n", s.getLat(), s.getLng()));

        Assertions.assertThat(places).hasSize(2);
    }

    @Test
    @DisplayName("Id를_통한_검색")
    public void findById() {
        Place ssuUniv = Place.builder()
                .lat(37.494705526855)
                .lng(126.95994559383)
                .placeUrl("ssu.ac.kr")
                .placeName("숭실대학교")
                .addressName("상도동")
                .addressRoadName("상도로")
                .build();

        Place cauUniv = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("cau.ac.kr")
                .placeName("중앙대학교")
                .addressName("서울")
                .addressRoadName("흑석로")
                .phoneNumber("02.820.1234")
                .build();

        placeService.insertPlace(ssuUniv);
        placeService.insertPlace(cauUniv);

        Place ssuPlace = placeService.findPlaceById(ssuUniv.getId());
        Place cauPlace = placeService.findPlaceById(cauUniv.getId());

        Assertions.assertThat(ssuPlace.getId()).isEqualTo(ssuUniv.getId());
        Assertions.assertThat(cauPlace.getId()).isEqualTo(cauUniv.getId());
    }

    @Test
    @DisplayName("이름을 통한 검색")
    public void findByName() {
        Place ssuUniv = Place.builder()
                .lat(37.494705526855)
                .lng(126.95994559383)
                .placeUrl("ssu.ac.kr")
                .placeName("숭실대학교")
                .addressName("상도동")
                .addressRoadName("상도로")
                .build();

        Place cauUniv = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("cau.ac.kr")
                .placeName("중앙대학교")
                .addressName("서울")
                .addressRoadName("흑석로")
                .phoneNumber("02.820.1234")
                .build();

        Place kakao = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("kakao")
                .placeName("카카오")
                .addressName("서울")
                .addressRoadName("판교로")
                .phoneNumber("02.1234.1234")
                .build();

        Place naver = Place.builder()
                .lat(37.505171184465496)
                .lng(126.95522473210741)
                .placeUrl("naver")
                .placeName("네이버")
                .addressName("서울")
                .addressRoadName("판교로")
                .phoneNumber("02.1234.1234")
                .build();

        placeService.insertPlace(ssuUniv);
        placeService.insertPlace(cauUniv);
        placeService.insertPlace(kakao);
        placeService.insertPlace(naver);

        List<Place> universities = placeService.findPlacesByName("대학교");
        List<Place> allPlaces = placeService.getAllPlaces();

        assertThat(universities, hasSize(2));
        Assertions.assertThat(allPlaces.size()).isEqualTo(4);
    }
}
