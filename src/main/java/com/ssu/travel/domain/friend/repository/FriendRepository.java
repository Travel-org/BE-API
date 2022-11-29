package com.ssu.travel.domain.friend.repository;

import com.ssu.travel.domain.friend.entity.Friend;
import com.ssu.travel.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByFolloweeAndFollower(User followee, User follower);

    @Query(value = "select distinct f from Friend f left join fetch f.follower where f.followee = :followee and f.isFriend = true",
            countQuery = "select count(f) from Friend f where f.followee = :followee and f.isFriend = true")
    Page<Friend> findFriendsByFollowee(@Param("followee") User followee, Pageable pageable);

    @Query(value = "select distinct f from Friend f left join fetch f.followee where f.follower = :follower and f.isFriend = false",
            countQuery = "select count(f) from Friend f where f.follower = :follower and f.isFriend = false ")
    Page<Friend> findGivenRequestsByFollower(@Param("follower") User follower, Pageable pageable);

    @Query(value = "select distinct f from Friend f left join fetch f.follower where f.followee = :followee and f.isFriend = false",
            countQuery = "select count(f) from Friend f where f.followee = :followee and f.isFriend = false ")
    Page<Friend> findGivingRequestsByFollowee(@Param("followee") User followee, Pageable pageable);

    @Modifying
    @Query("delete from Friend f where f.followee = :followee and f.follower = :follower")
    void deleteByFolloweeAndFollower(@Param("followee") User followee, @Param("follower") User follower);
}
