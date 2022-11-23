package com.ssu.travel.domain.comment.repository;

import com.ssu.travel.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
