package com.ssu.travel.domain.post.repository;

import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p join fetch p.user left join fetch p.schedule where p.user = :user order by p.id desc")
    List<Post> findAllByUserOrderByPostIdDesc(User user);

    @Query(value = "select distinct p from Post p left join fetch p.user u left join fetch p.schedule s left join fetch s.place where u.id in :userIds",
            countQuery = "select count(p) from Post p where p.user.id in :userIds")
    Page<Post> findPostsByUserIds(@Param("userIds") List<Long> userIds, Pageable pageable);

    List<Post> findAllByUser(User user);

    @Query("select distinct p from Post p left join fetch p.comments left join p.photos where p.id = :postId")
    Optional<Post> findPostWithCommentAndPhotosByeId(@Param("postId") Long postId);

}
