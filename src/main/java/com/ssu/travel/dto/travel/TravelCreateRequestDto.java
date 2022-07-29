package com.ssu.travel.dto.travel;

import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TravelCreateRequestDto {

    @NotNull(message = "유저 아이디가 필요합니다.")
    private Long userId;
    @NotNull(message = "제목이 필요합니다.")
    private String title;
    @NotNull(message = "공개 여부 결정이 필요합니다.")
    private TravelType travelType;
    @NotNull(message = "시작 날짜가 필요합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "종료 날짜가 필요합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public TravelCreateRequestDto(@NonNull Long userId, @NonNull String title, @NonNull TravelType travelType,
                                  @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        this.userId = userId;
        this.title = title;
        this.travelType = travelType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Travel toEntity() {
        return Travel.builder()
                .managerId(userId)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .travelType(travelType)
                .build();
    }
}
