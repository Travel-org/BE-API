package com.ssu.travel.domain.schedulePhoto.dto;

import com.ssu.travel.domain.schedulePhoto.entity.SchedulePhoto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchedulePhotoDto {

    private final Long schedulePhotoId;
    private final Long userId;
    private final Long scheduleId;
    private final String schedulePhotoPath;

    public static SchedulePhotoDto from(SchedulePhoto schedulePhoto) {
        return SchedulePhotoDto.builder()
                .schedulePhotoId(schedulePhoto.getId())
                .userId(schedulePhoto.getUser().getId())
                .schedulePhotoPath(schedulePhoto.getPhotoPath())
                .build();
    }
}


