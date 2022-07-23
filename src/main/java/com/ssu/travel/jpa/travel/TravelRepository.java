package com.ssu.travel.jpa.travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    @Query("SELECT t FROM Travel t ORDER BY t.id DESC")
    List<Travel> findAllDesc();
}
