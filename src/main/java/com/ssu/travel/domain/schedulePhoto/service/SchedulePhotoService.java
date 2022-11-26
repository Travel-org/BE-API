package com.ssu.travel.domain.schedulePhoto.service;

import static java.util.stream.Collectors.toList;

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
        userService.findUserEntityById(user.getId());
        Schedule schedule = scheduleService.findScheduleEntityById(scheduleId);

        //  TODO: photo 저장 (S3 or ec2 자체에 저장할지 고민)
        List<SchedulePhoto> schedulePhotos = null;
        schedulePhotoRepository.saveAll(schedulePhotos);
        schedule.addSchedulePhotos(schedulePhotos);

        return schedulePhotos.stream()
                .map(SchedulePhotoDto::from)
                .collect(toList());
    }

    // TODO: schedulePhoto
    public List<SchedulePhotoDto> getSchedulePhotos(Long scheduleId) {
        Schedule schedule = scheduleService.findScheduleEntityById(scheduleId);
        return schedule.getPhotos().stream()
                .map(SchedulePhotoDto::from)
                .collect(toList());
    }

    public void deleteSchedulePhotos(Long scheduleId, List<Long> schedulePhotoIds) {
        Schedule schedule = scheduleService.findScheduleEntityById(scheduleId);
        List<SchedulePhoto> schedulePhotos =
                schedulePhotoRepository.findAllById(schedulePhotoIds);
        schedule.removeSchedulePhotos(schedulePhotos);
        schedulePhotoRepository.deleteAll(schedulePhotos);
    }
}
