package com.ssu.travel.jpa.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.jpa.user.UserRepository;
import com.ssu.travel.jpa.usertravel.UserTravelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TravelRepositoryTest {

    @Autowired
    TravelRepository travelRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserTravelRepository userTravelRepository;

    private final String title = "테스트 title";
    private final LocalDate startDate = LocalDate.now();
    private final LocalDate endDate = LocalDate.of(2022, 12, 25);
    private final Long managerId = 1L;

    private final TravelType travelType = TravelType.PUBLIC;

    @Test
    void createTravel() {
        // given
        TravelCreateRequestDto travelCreateRequestDto = TravelCreateRequestDto.builder()
                .title(title)
                .travelType(travelType)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        Travel result = travelRepository.save(travelCreateRequestDto.toEntity());

        // then
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getManagerId()).isEqualTo(managerId);
        assertThat(result.getStartDate()).isEqualTo(startDate);
        assertThat(result.getEndDate()).isEqualTo(endDate);
    }
}