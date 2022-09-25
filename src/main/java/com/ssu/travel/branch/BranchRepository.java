package com.ssu.travel.branch;

import com.ssu.travel.schedule.Schedule;
import com.ssu.travel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("select b from Branch b where b.user = :user and b.schedule = :schedule")
    Optional<Branch> findByUserAndSchedule(@Param("user") User user, @Param("schedule") Schedule schedule);
}
