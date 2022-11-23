package com.ssu.travel.domain.comment.service;

import com.ssu.travel.domain.comment.dto.CommentDto;
import com.ssu.travel.domain.comment.exception.CommentNotFoundException;
import com.ssu.travel.domain.comment.web.request.CreateCommentRequest;
import com.ssu.travel.domain.comment.web.request.UpdateCommentRequest;
import com.ssu.travel.domain.comment.entity.Comment;
import com.ssu.travel.domain.comment.exception.CommentException;
import com.ssu.travel.domain.comment.repository.CommentRepository;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.post.service.PostService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Transactional
    public CommentDto saveComment(User user, Long postId, CreateCommentRequest request) {
        User userEntity = userService.findUserEntityById(user.getId());
        Post post = postService.findPostById(postId);

        Comment comment = Comment.of(userEntity, post, request.getContent());
        commentRepository.save(comment);
        return CommentDto.from(comment);
    }

    @Transactional
    public CommentDto updateComment(User user, Long commentId, UpdateCommentRequest request) {
        User userEntity = userService.findUserEntityById(user.getId());
        Comment comment = findCommentById(commentId);

        if (hasPermission(user, userEntity)) {
            throw new CommentException("댓글에 대한 권한이 없습니다.");
        }
        comment.updateContent(request.getContent());
        return CommentDto.from(comment);
    }

    @Transactional
    public void deleteComment(User user, Long commentId) {
        User userEntity = userService.findUserEntityById(user.getId());
        Comment comment = findCommentById(commentId);
        if (hasPermission(user, userEntity)) {
            throw new CommentException("댓글에 대한 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
    private boolean hasPermission(User user, User commenter) {
        return commenter.equals(user);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("등록된 댓글이 존재하지 않습니다."));
    }
}
