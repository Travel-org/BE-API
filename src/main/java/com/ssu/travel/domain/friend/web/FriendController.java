package com.ssu.travel.domain.friend.web;

import com.ssu.travel.domain.friend.service.FriendService;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/api/friend/{targetEmail}")
    public ResponseEntity sendFriendRequest(@LoginUser User user,
                                            @PathVariable String targetEmail) {
        SimpleUserDto userDto = friendService.sendFriendRequest(user.getId(), targetEmail);

        return ResponseEntity.ok(Result.createSuccessResult(userDto));
    }

    @PostMapping("/api/friend/giving-requests/{targetId}")
    public ResponseEntity acceptFriendRequest(@LoginUser User user, @PathVariable Long targetId) {
        friendService.acceptFriendRequest(user.getId(), targetId);
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }

    @DeleteMapping("/api/friend/given-requests/{targetId}")
    public ResponseEntity<Void> rejectFriendRequest(@LoginUser User user, @PathVariable Long targetId) {
        friendService.rejectFriendRequest(user.getId(), targetId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("api/friend/{targetId}")
    public ResponseEntity cancelFriend(@LoginUser User user, @PathVariable Long targetId) {
        friendService.cancelFriend(user.getId(), targetId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/friend/friends")
    public ResponseEntity getMyFriends(@LoginUser User user, Pageable pageable) {
        Page<SimpleUserDto> friends = friendService.getFriends(user.getId(), pageable);
        return ResponseEntity.ok(Result.createSuccessResult(friends));
    }

    @GetMapping("/api/friend/giving-requests")
    public ResponseEntity getMyGivingRequests(@LoginUser User user, Pageable pageable) {
        Page<SimpleUserDto> friends = friendService.getGivingRequests(user.getId(), pageable);
        return ResponseEntity.ok(Result.createSuccessResult(friends));
    }

    @GetMapping("/api/friend/given-requests")
    public ResponseEntity getMyGivenRequests(@LoginUser User user, Pageable pageable) {
        Page<SimpleUserDto> friends = friendService.getGivenRequests(user.getId(), pageable);
        return ResponseEntity.ok(Result.createSuccessResult(friends));
    }

}
