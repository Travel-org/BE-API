package com.ssu.travel.usercost;

import com.ssu.travel.cost.Cost;
import com.ssu.travel.user.User;
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
    void updateAmount(@Param("amount") Long amount, @Param("userCost") Long userCostId);
}
