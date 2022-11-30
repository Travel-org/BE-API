package com.ssu.travel.domain.post.service;

import com.ssu.travel.domain.friend.repository.FriendRepository;
import com.ssu.travel.domain.photo.service.PhotoService;
import com.ssu.travel.domain.post.dto.PostDto;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.post.exception.PostNotFoundException;
import com.ssu.travel.domain.post.repository.PostRepository;
import com.ssu.travel.domain.post.web.request.CreatePostRequest;
import com.ssu.travel.domain.post.web.request.UpdatePostRequest;
import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.schedule.service.ScheduleService;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    // TODO: FriendService
    private final FriendRepository friendRepository;

    private final UserService userService;
    private final ScheduleService scheduleService;
    private final PhotoService photoService;

    @Transactional
    public PostDto savePost(Long userId, CreatePostRequest request) {
        User user = userService.findUserById(userId);
        Schedule schedule = scheduleService.findScheduleEntityById(request.getScheduleId());

        // post 생성
        Post post = Post.of(schedule, user, request.getTitle(), request.getText());

        // post 사진 저장
        if (request.getPhotos() != null && !request.getPhotos().isEmpty()) {
            photoService.savePhotos(post, request.getPhotos());
        }

        postRepository.save(post);

        return PostDto.from(post);
    }

    public PostDto getPostById(Long postId) {
        Post post = findPostWithCommentsAndPhotosById(postId);
        return PostDto.from(post);
    }


    public Page<PostDto> getFriendPosts(Long userId, Pageable pageable) {
        List<Long> friendIds = friendRepository.findFriendsByFollowee(userId, pageable)
                .map(friend -> friend.getFollower().getId())
                .toList();

        return postRepository.findPostsByUserIds(friendIds, pageable)
                .map(PostDto::from);
    }

    @Transactional
    public PostDto updatePost(Long postId, UpdatePostRequest request) {
        Post post = findPostById(postId);

        post.update(request.getTitle(), request.getText());

        if (request.getAddPhotos() != null && !request.getAddPhotos().isEmpty()) {
            photoService.savePhotos(post, request.getAddPhotos());
        }

        if (request.getRemovePhotoIds() != null && !request.getRemovePhotoIds().isEmpty()) {
            photoService.removePhotoByIds(request.getRemovePhotoIds());
        }

        postRepository.flush();
        return PostDto.from(post);
    }


    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    /**
     * admin 메서드
     */
    public Page<PostDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostDto::from);
    }

    /**
     * Entity 조회 메서드
     */
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("등록된 Post가 존재하지 않습니다."));
    }

    public Post findPostWithCommentsAndPhotosById(Long postId) {
        return postRepository.findPostWithCommentAndPhotosByeId(postId)
                .orElseThrow(() -> new PostNotFoundException("등록된 Post가 존재하지 않습니다."));
    }
}
