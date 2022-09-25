package com.ssu.travel.travel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("select distinct t from Travel t left join fetch t.userTravels ut left join fetch ut.user where t.id = :travelId")
    Optional<Travel> findTravelWithUserById(Long travelId);

    @Query("select t from Travel t order by t.id desc")
    List<Travel> findAllDesc();

    @Query("select t from Travel t join t.userTravels ut join ut.user u where u.id = :userId")
    Page<Travel> findTravelsByUserId(@Param("userId") Long userId, Pageable pageable);
}
