package com.ssu.travel.domain.friend.service;

import com.ssu.travel.domain.friend.dto.FriendDto;
import com.ssu.travel.domain.friend.entity.Friend;
import com.ssu.travel.domain.friend.exception.FriendNotFoundException;
import com.ssu.travel.domain.friend.repository.FriendRepository;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.post.repository.PostRepository;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import com.ssu.travel.global.exception.custom.DuplicatedRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    /**
     * <h1>targetEmail에게 following 요청</h1>
     *
     * @param userId
     * @param targetEmail
     * @return
     */
    @Transactional
    public SimpleUserDto sendFriendRequest(Long userId, String targetEmail) {
        User user = userService.findUserById(userId);
        User targetUser = userService.findUserByEmail(targetEmail);

        checkFriendRequest(user, targetUser, "이미 친구 요청을 보냈습니다.");
        checkFriendRequest(targetUser, user, "이미 친구 요청이 와있습니다.");

        friendRepository.save(Friend.of(user, targetUser));
        return SimpleUserDto.from(targetUser);
    }

    /**
     * <h1>following request 취소</h1>
     *
     * @param userId
     * @param targetUserId
     */
    @Transactional
    public void cancelFollowingRequest(Long userId, Long targetUserId) {
        User user = userService.findUserById(userId);
        User targetUser = userService.findUserById(targetUserId);
        Friend friend = findFriendByUserAndTarget(user, targetUser);
        if (friend.getIsFriend()) {
            throw new DuplicatedRequestException("이미 친구 요청이 수락 되었습니다.");
        }

        friendRepository.delete(friend);
    }

    /**
     * user, targetUser 절교 메서드
     * @param userId
     * @param targetId
     */
    @Transactional
    public void cancelFriend(Long userId, Long targetId) {
        User user = userService.findUserById(userId);
        User targetUser = userService.findUserById(targetId);

        friendRepository.deleteByFolloweeAndFollower(user, targetUser);
        friendRepository.deleteByFolloweeAndFollower(targetUser, user);
    }


    public Page<SimpleUserDto> getGivingRequests(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);

        return friendRepository.findGivingRequestsByFollowee(user, pageable)
                .map(Friend::getFollower)
                .map(SimpleUserDto::from);
    }

    public Page<SimpleUserDto> getGivenRequests(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);

        return friendRepository.findGivenRequestsByFollower(user, pageable)
                .map(Friend::getFollowee)
                .map(SimpleUserDto::from);
    }

    public Page<SimpleUserDto> getFriends(Long userId, Pageable pageable) {
        User user = userService.findUserById(userId);

        return friendRepository.findFriendsByFollowee(user, pageable)
                .map(Friend::getFollower)
                .map(SimpleUserDto::from);
    }

    public FriendDto getFriendById(Long targetId) {
        User targetUser = userService.findUserById(targetId);
        List<Post> posts = postRepository.findAllByUser(targetUser);
        return FriendDto.from(targetUser, posts);
    }


    /**
     * <h1>following 요청 수락</h1>
     *
     * @param userId
     * @param targetUserId
     */
    @Transactional
    public void acceptFriendRequest(Long userId, Long targetUserId) {
        User user = userService.findUserById(userId);
        User targetUser = userService.findUserById(targetUserId);

        Friend friend = findFriendByUserAndTarget(user, targetUser);
        friend.acceptFriend();

        friendRepository.save(Friend.of(targetUser, user, true));
    }

    @Transactional
    public void rejectFriendRequest(Long userId, Long targetUserId) {
        User user = userService.findUserById(userId);
        User targetUser = userService.findUserById(targetUserId);

        Friend friend = findFriendByUserAndTarget(user, targetUser);
        friendRepository.delete(friend);
    }


    /**
     * private Method 시작
     */
    private void checkFriendRequest(User user, User targetUser, String message) {
        Optional<Friend> flag = friendRepository.findByFolloweeAndFollower(user, targetUser);
        if (flag.isPresent()) {
            if (flag.get().getIsFriend()) {
                throw new DuplicatedRequestException("친구 요청이 이전에 수락되었습니다.");
            } else {
                throw new DuplicatedRequestException(message);
            }
        }
    }

    /**
     * Entity 조회 메서드
     */
    public Friend findFriendByUserAndTarget(User user, User targetUser) {
        return friendRepository.findByFolloweeAndFollower(user, targetUser)
                .orElseThrow(() -> new FriendNotFoundException("해당 친구 요청이 존재하지 않습니다."));
    }
}
