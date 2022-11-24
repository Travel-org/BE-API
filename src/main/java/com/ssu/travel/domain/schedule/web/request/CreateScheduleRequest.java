package com.ssu.travel.domain.schedule.web.request;

import com.ssu.travel.domain.place.web.request.CreatePlaceRequest;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class CreateScheduleRequest {

    @NotNull
    private CreatePlaceRequest place;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @NotNull
    private List<Long> userIds;
}
