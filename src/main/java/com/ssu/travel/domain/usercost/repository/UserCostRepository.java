package com.ssu.travel.domain.usercost.repository;

import com.ssu.travel.domain.usercost.entity.UserCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCostRepository extends JpaRepository<UserCost, Long> {

    @Modifying
    @Query("delete from UserCost uc where uc.user.id = :userId and uc.cost.id = :costId")
    void deleteByUserIdAndCostId(@Param("userId") Long userId, @Param("costId") Long costId);

    @Modifying
    @Query("update UserCost uc set uc.amount = :amount where uc.id = :userCostId")
    void updateAmountById(@Param("amount") Long amount, @Param("userCostId") Long userCostId);
}
