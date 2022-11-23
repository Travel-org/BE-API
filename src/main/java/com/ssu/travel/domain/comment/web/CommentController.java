package com.ssu.travel.domain.comment.web;

import com.ssu.travel.domain.comment.dto.CommentDto;
import com.ssu.travel.domain.comment.web.request.CreateCommentRequest;
import com.ssu.travel.domain.comment.web.request.UpdateCommentRequest;
import com.ssu.travel.domain.comment.service.CommentService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    public ResponseEntity saveComment(@LoginUser User user,
                                      @PathVariable Long postId,
                                      @RequestBody @Validated CreateCommentRequest request) {
        CommentDto commentDto = commentService.saveComment(user, postId, request);
        return ResponseEntity.ok(Result.createSuccessResult(commentDto));
    }


    @PutMapping("/api/comment/{commentId}")
    public ResponseEntity updateComment(@LoginUser User user,
                                        @PathVariable Long commentId,
                                        @RequestBody @Validated UpdateCommentRequest request) {
        CommentDto commentDto = commentService.updateComment(user, commentId, request);
        return ResponseEntity.ok(Result.createSuccessResult(commentDto));
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity deleteComment(@LoginUser User user, @PathVariable Long commentId) {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }
}
