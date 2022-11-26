package com.ssu.travel.domain.branch.repository;

import com.ssu.travel.domain.branch.entity.Branch;
import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query("select b from Branch b where b.user.id = :userId and b.schedule.id = :scheduleId")
    Optional<Branch> findByUserIdAndScheduleId(@Param("user") Long userId, @Param("schedule") Long scheduleId);
}
