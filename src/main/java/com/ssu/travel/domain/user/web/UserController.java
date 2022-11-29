package com.ssu.travel.domain.user.web;

import com.ssu.travel.domain.storage.dto.ImageDto;
import com.ssu.travel.domain.user.dto.UserDto;
import com.ssu.travel.domain.user.dto.UserWithPostDto;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.service.UserService;
import com.ssu.travel.domain.user.web.request.ModifyUserRequest;
import com.ssu.travel.global.dto.Result;
import com.ssu.travel.global.security.annotation.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/duplication/nickname")
    public ResponseEntity checkNicknameDuplication(@LoginUser User user, @RequestParam String nickname) {
        userService.checkNicknameDuplication(nickname, user.getId());
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }

    @GetMapping("/api/user/{userId}")
    public ResponseEntity getOtherUserInfo(@LoginUser User user, @PathVariable Long userId) {
        UserDto userDto = userService.getOtherUserInfo(user.getId(), userId);
        return ResponseEntity.ok(Result.createSuccessResult(userDto));
    }

    //내 정보 수정
    @PostMapping("/api/user")
    public ResponseEntity updateMyInfo(@LoginUser User user,
                                       @RequestBody @Validated ModifyUserRequest request) {

        userService.updateMyInfo(user.getId(), request);
        return ResponseEntity.ok(Result.createSuccessResult(null));
    }

    //내 정보 조회
    @GetMapping("/api/user")
    public ResponseEntity getMyInfo(@LoginUser User user) {
        UserWithPostDto myInfo = userService.getMyProfile(user.getId());
        return ResponseEntity.ok(Result.createSuccessResult(myInfo));
    }

    @GetMapping("/api/user/profile")
    public ResponseEntity getProfileImageSource(@LoginUser User user) {
        ImageDto imageDto = userService.getUserProfileImageSource(user.getId());
        return ResponseEntity.ok(Result.createSuccessResult(imageDto));
    }

    /**
     * Admin
     */
    @GetMapping("/api/user/users")
    public ResponseEntity getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(Result.createSuccessResult(users));
    }
}
