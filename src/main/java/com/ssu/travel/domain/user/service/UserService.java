package com.ssu.travel.domain.user.service;


import static java.util.stream.Collectors.*;

import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.post.repository.PostRepository;
import com.ssu.travel.domain.storage.dto.ImageDto;
import com.ssu.travel.domain.storage.service.StorageService;
import com.ssu.travel.domain.storage.util.Base64Decoder;
import com.ssu.travel.domain.storage.util.FileNameGenerator;
import com.ssu.travel.domain.user.dto.UserDto;
import com.ssu.travel.domain.user.dto.UserWithPostDto;
import com.ssu.travel.domain.user.web.request.ModifyUserRequest;
import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.domain.user.exception.DuplicatedNicknameException;
import com.ssu.travel.domain.user.exception.UserNotFoundException;
import com.ssu.travel.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StorageService storageService;
    private final Base64Decoder base64Decoder;
    private final FileNameGenerator fileNameGenerator;
    private final PostRepository postRepository;

    @Value("${app.user.default-image}")
    private String DEFAULT_IMAGE_PATH;

    public void checkNicknameDuplication(String nickname, Long userId) {
        User user = findUserById(userId);

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException("중복된 닉네임입니다.");
        }
        if (user.getNickname() == null) {
            return;
        }

        if (user.getNickname().equals(nickname)) {
            return;
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException("중복된 닉네임입니다.");
        }
    }

    @Transactional
    public void updateMyInfo(Long userId, ModifyUserRequest request) {
        // nickname
        checkNicknameDuplication(request.getNickname(), userId);

        User user = findUserById(userId);

        if (user.getProfileImagePath().equals(DEFAULT_IMAGE_PATH)) {
            if (request.getProfileImage().getImageSource() == null || request.getProfileImage().getImageSource()
                    .equals("")) {

                user.update(request.getNickname(), request.getGender(), request.getBirthday(),
                        DEFAULT_IMAGE_PATH);
                return;
            }
            //파일 등록
            String imagePath = registerImage(request);
            user.update(request.getNickname(), request.getGender(), request.getBirthday(), imagePath);
            return;
        }

        //기존 삭제
        storageService.delete(user.getProfileImagePath());

        //설정 -> 기본
        if (request.getProfileImage().getImageSource() == null || request.getProfileImage()
                .getImageSource().equals("")) {

            user.update(request.getNickname(), request.getGender(), request.getBirthday(),
                    DEFAULT_IMAGE_PATH);
            return;
        }

        //설정 -> 설정
        //파일 등록
        String imagePath = registerImage(request);
        user.update(request.getNickname(), request.getGender(), request.getBirthday(), imagePath);
    }

    @Transactional
    public String registerImage(ModifyUserRequest request) {
        String encodedImageSource = request.getProfileImage().getImageSource();
        byte[] imageSource = base64Decoder.decode(encodedImageSource);
        String fileName = fileNameGenerator.generateRandomName().concat(".")
                .concat(request.getProfileImage().getUserProfileExtension());
        return storageService.store(imageSource, fileName);
    }

    public UserWithPostDto getMyProfile(Long userId) {
        User user = findUserById(userId);
        List<Post> posts = postRepository.findAllByUser(user);
        return UserWithPostDto.from(user, posts);
    }

    public UserDto getOtherUserInfo(Long userId, Long otherUserId) {
        User user = findUserById(userId);
        User targetUser = findUserById(otherUserId);

        checkEnteredInformation(targetUser);
        return UserDto.from(user);
    }

    public ImageDto getUserProfileImageSource(Long userId) {
        User user = findUserById(userId);

        if (user.getProfileImagePath().equals(DEFAULT_IMAGE_PATH)) {
            // TODO: default iamge 제공
            return ImageDto.of("default", "jpg");
        }
        return storageService.getFileSourceAndExtension(user.getProfileImagePath());
    }


    /**
     * Admin 메서드
     */
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto::from);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(toList());
    }


    /**
     * Private 메서드
     */
    private void checkEnteredInformation(User user) {
        if (!user.isInformationRequired()) {
            throw new UserNotFoundException("추가 정보를 입력하지 않은 계정입니다.");
        }
    }

    /**
     * Entity 조회 메서드
     */
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }
}
