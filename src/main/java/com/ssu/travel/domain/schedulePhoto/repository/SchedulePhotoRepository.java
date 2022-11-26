package com.ssu.travel.domain.schedulePhoto.repository;

import com.ssu.travel.domain.schedulePhoto.entity.SchedulePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchedulePhotoRepository extends JpaRepository<SchedulePhoto, Long> {

    @Query("select sp from SchedulePhoto sp where sp.schedule.id = :scheduleId")
    List<SchedulePhoto> findAllByScheduleId(@Param("scheduleId") Long scheduleId);

    @Query("select sp from SchedulePhoto sp where sp.schedule.id in :scheduleIds")
    List<SchedulePhoto> findAllByScheduleIds(@Param("scheduleIds") List<Long> scheduleIds);
}
