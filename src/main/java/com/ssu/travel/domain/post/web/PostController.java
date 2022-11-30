package com.ssu.travel.domain.post.web;

import com.ssu.travel.domain.post.dto.PostDto;
import com.ssu.travel.domain.post.service.PostService;
import com.ssu.travel.domain.post.web.request.CreatePostRequest;
import com.ssu.travel.domain.post.web.request.UpdatePostRequest;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post")
    public ResponseEntity savePost(@LoginUser User user,
                                   @RequestBody @Validated CreatePostRequest request) {
        PostDto post = postService.savePost(user.getId(), request);

        return ResponseEntity.ok(Result.createSuccessResult(post));
    }

    @GetMapping("/api/post/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(Result.createSuccessResult(postDto));
    }

    // TODO: storageService
    @PatchMapping("/api/post/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId,
                                     @RequestBody @Validated UpdatePostRequest request) {
        PostDto postDto = postService.updatePost(postId, request);
        return ResponseEntity.ok(Result.createSuccessResult(postDto));
    }

    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }
}
