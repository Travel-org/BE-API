package com.ssu.travel.usercost;

import com.ssu.travel.cost.Cost;
import com.ssu.travel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCostRepository extends JpaRepository<UserCost, Long> {

    @Modifying
    @Query("delete from UserCost uc where uc.cost = :cost and uc.user = :user")
    void deleteByCostAndUser(@Param("cost") Cost cost, @Param("user") User user);

    @Modifying
    @Query("update UserCost uc set uc.amount = :amount where uc = :userCost")
    void updateByUserCostId(@Param("amount") Long amount, @Param("userCost") UserCost userCost);
}
