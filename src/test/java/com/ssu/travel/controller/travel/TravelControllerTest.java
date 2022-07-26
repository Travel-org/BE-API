package com.ssu.travel.controller.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.jpa.travel.TravelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TravelControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TravelRepository travelRepository;

    @AfterEach
    void cleanUp() {
        travelRepository.deleteAll();
    }

    @Test
    void createTravel() {
        // given
        String title = "title";
        LocalDate now = LocalDate.now();
        TravelCreateRequestDto requestDto = TravelCreateRequestDto.builder()
                .title(title)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();

        String url = "http://localhost:" + port + "/v1/travels";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<com.ssu.travel.jpa.travel.Travel> all = travelRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getStartDate()).isEqualTo(now);
        assertThat(all.get(0).getEndDate()).isEqualTo(now);
    }
}