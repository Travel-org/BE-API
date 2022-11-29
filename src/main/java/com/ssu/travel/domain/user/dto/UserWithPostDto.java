package com.ssu.travel.domain.user.dto;

import static java.util.stream.Collectors.*;
import static lombok.AccessLevel.PRIVATE;

import com.ssu.travel.domain.post.dto.PostDto;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.entity.Gender;
import com.ssu.travel.domain.user.entity.Role;
import com.ssu.travel.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
public class UserWithPostDto {

    private final Long id;
    private final String email;
    private final String nickname;
    private final Gender gender;
    private final LocalDate birthDay;
    private final String profileImagePath;
    private final Role role;
    private final List<PostDto> posts;

    public static UserWithPostDto from(User user, List<Post> posts) {
        return UserWithPostDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .birthDay(user.getBirthday())
                .profileImagePath(user.getProfileImagePath())
                .role(user.getRole())
                .posts(posts.stream()
                        .map(PostDto::from)
                        .collect(toList()))
                .build();
    }
}
