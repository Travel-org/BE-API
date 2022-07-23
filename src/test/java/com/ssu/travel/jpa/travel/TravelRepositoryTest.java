package com.ssu.travel.jpa.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TravelRepositoryTest {

    @Autowired
    TravelRepository travelRepository;

    @AfterEach
    void cleanUp() {
        travelRepository.deleteAll();
    }

    @Test
    void travelList() {

        LocalDate startDate = LocalDate.of(2022, 07, 23);
        LocalDate endDate = LocalDate.of(2022, 12, 25);

        TravelCreateRequestDto dto = TravelCreateRequestDto.builder()
                .title("테스트 title")
                .memo("테스트 memo")
                .startDate(startDate)
                .endDate(endDate)
                .travelType(TravelType.PUBLIC)
                .build();

        travelRepository.save(dto.toEntity());

        // when 
        List<Travel> travels = travelRepository.findAll();
        
        // then 
        Travel res = travels.get(0);
        assertThat(res.getTitle()).isEqualTo("테스트 title");
        assertThat(res.getMemo()).isEqualTo("테스트 memo");
        assertThat(res.getStartDate().isEqual(startDate));
        assertThat(res.getEndDate().isEqual(endDate));
    }
}