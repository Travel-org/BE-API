package com.ssu.travel.travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("select distinct t from Travel t left join fetch t.userTravels ut left join fetch ut.user where t.id = :travelId")
    Optional<Travel> findTravelById(Long travelId);

    @Query("SELECT t FROM Travel t ORDER BY t.id DESC")
    List<Travel> findAllDesc();

}
