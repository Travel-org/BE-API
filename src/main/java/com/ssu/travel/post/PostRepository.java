package com.ssu.travel.post;

import com.ssu.travel.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p join fetch p.user left join fetch p.schedule where p.user = :user order by p.id desc")
    List<Post> findAllByUserOrderByPostIdDesc(User user);

    @Query(value = "select distinct p from Post p left join fetch p.user u left join fetch p.schedule s left join fetch s.place where u.id in :userIds",
            countQuery = "select count(p) from Post p where p.user.id in :userIds")
    Page<Post> findPostsByUserIds(@Param("userIds") List<Long> userIds, Pageable pageable);
}
