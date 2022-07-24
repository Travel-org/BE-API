package com.ssu.travel.dto.travel;

import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TravelCreateRequestDto {
    private Long userId;
    private String title;
    private TravelType travelType;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public TravelCreateRequestDto(Long userId, String title, TravelType travelType, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.title = title;
        this.travelType = travelType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Travel toEntity() {
        return Travel.builder()
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .travelType(travelType)
                .build();
    }
}
