package com.ssu.travel.domain.cost.repository;

import com.ssu.travel.domain.cost.entity.Cost;
import com.ssu.travel.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost, Long> {

    @Query("select distinct c from Cost c join c.travel t join fetch c.userCosts where t = :travel")
    List<Cost> findAllByTravel(@Param("travel") Travel travel);

    @Query("select distinct c from Cost c join fetch c.userCosts uc join fetch uc.user "
            + "where c.travel.id = :travelId and c.id = :costId")
    Optional<Cost> findCostWithUserByTravelIdAndCostId(@Param("travelId") Long travelId,
                                                       @Param("costId") Long costId);

    @Query("select distinct c from Cost c where c.travel.id = :travelId and c.id = :costId")
    Optional<Cost> findCostByTravelIdAndCostId(@Param("travelId") Long travelId, @Param("costId") Long costId);
}
