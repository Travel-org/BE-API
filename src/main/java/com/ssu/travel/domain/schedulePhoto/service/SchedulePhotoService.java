package com.ssu.travel.domain.schedulePhoto.service;

import static java.util.stream.Collectors.*;

import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.schedule.service.ScheduleService;
import com.ssu.travel.domain.schedulePhoto.dto.SchedulePhotoDto;
import com.ssu.travel.domain.schedulePhoto.entity.SchedulePhoto;
import com.ssu.travel.domain.schedulePhoto.repository.SchedulePhotoRepository;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulePhotoService {

    private final SchedulePhotoRepository schedulePhotoRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;

    public List<SchedulePhotoDto> saveSchedulePhotos(User user, Long scheduleId, List<MultipartFile> photos) {
        User userEntity = userService.findUserEntityById(user.getId());
        Schedule schedule = scheduleService.findScheduleEntityById(scheduleId);
        //  TODO: photo 저장
        List<SchedulePhoto> schedulePhotos = null;
        schedulePhotoRepository.saveAll(schedulePhotos);
        schedule.addSchedulePhotos(schedulePhotos);
        return schedulePhotos.stream()
                .map(SchedulePhotoDto::from)
                .collect(toList());
    }
}
