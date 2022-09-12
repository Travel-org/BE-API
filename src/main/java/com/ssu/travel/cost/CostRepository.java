package com.ssu.travel.cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CostRepository extends JpaRepository<Cost, Long> {
    @Query("select distinct c from Cost c join c.travel t join fetch c.userCosts where t.id = :travelId")
    List<Cost> findCostsByTravelId(@Param("travelId") Long travelId);

    @Query("select distinct c from Cost c join fetch c.userCosts uc join fetch uc.user where c.id = :costId")
    Optional<Cost> getCostById(@Param("costId") Long costId);
}
