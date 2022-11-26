package com.ssu.travel.domain.schedule.web;

import com.ssu.travel.domain.schedule.dto.SchedulePhotoDto;
import com.ssu.travel.domain.schedule.service.SchedulePhotoService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class SchedulePhotoController {

    private final SchedulePhotoService schedulePhotoService;

    @PostMapping("/api/travel/{travelId}/schedule/{scheduleId}/photos")
    public ResponseEntity uploadSchedulePhotos(@LoginUser User user,
                                               @PathVariable Long travelId,
                                               @PathVariable Long scheduleId,
                                               @RequestPart List<MultipartFile> photos) {
        List<SchedulePhotoDto> schedulePhotoDtos = schedulePhotoService.saveSchedulePhotos(user, scheduleId, photos);
        return ResponseEntity.ok(Result.createSuccessResult(schedulePhotoDtos));
    }
}
