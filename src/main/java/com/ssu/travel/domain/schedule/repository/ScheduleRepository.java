package com.ssu.travel.domain.schedule.repository;

import com.ssu.travel.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s join fetch s.place where s.id = :scheduleId")
    Optional<Schedule> findWithPlaceByScheduleId(@Param("scheduleId") Long scheduleId);

    @Query("select distinct s from Schedule s left join fetch s.place left join s.travelDate td where td.date = :date and td.travel.id = :travelId")
    List<Schedule> findAllWithPlaceByDateAndTravelId(@Param("date") LocalDate date, @Param("travelId") Long travelId);

//    @Query("select distinct s from Schedule s left join fetch s.place left join s.travelDate td where td.travel = :travel and td.date = :date")
//    List<Schedule> findAllWithPlaceByDateAndTravelId(@Param("date") LocalDate date, @Param("travel") Travel travel);
}
