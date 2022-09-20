package com.ssu.travel.schedule;

import com.ssu.travel.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s join fetch s.place where s.id = :scheduleId")
    Optional<Schedule> findScheduleWithPlaceByScheduleId(@Param("scheduleId") Long scheduleId);

//    @Query("select distinct s from Schedule s left join fetch s.place left join s.travelDate td where td.travel = :travel and td.date = :date")
//    List<Schedule> findAllWithPlaceByDateAndTravelId(@Param("date") LocalDate date, @Param("travel") Travel travel);
}
