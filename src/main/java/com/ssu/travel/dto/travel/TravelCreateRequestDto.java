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
    private String title;
    private String memo;
    private TravelType travelType;
    private LocalDate startDate;
    private LocalDate endDate;



    @Builder
    public TravelCreateRequestDto(String title, String memo, TravelType travelType, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.memo = memo;
        this.travelType = travelType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Travel toEntity() {
        return Travel.builder()
                .title(title)
                .memo(memo)
                .startDate(startDate)
                .endDate(endDate)
                .travelType(travelType)
                .build();
    }
}
