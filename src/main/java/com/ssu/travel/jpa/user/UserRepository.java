package com.ssu.travel.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.kakaoId = :kakaoId")
    Optional<User> findByKakaoId(@Param("kakaoId") Long kakaoId);

    @Query("select u from Travel t join t.userTravels ut join ut.user u where t.id = :travelId")
    List<User> findUsersByTravelId(@Param("travelId") Long travelId);
}
