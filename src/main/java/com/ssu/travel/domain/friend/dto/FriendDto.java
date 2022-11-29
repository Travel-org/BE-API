package com.ssu.travel.domain.friend.dto;

import static java.util.stream.Collectors.*;
import static lombok.AccessLevel.*;

import com.ssu.travel.domain.post.dto.PostDto;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
public class FriendDto {

    private final String email;
    private final String nickname;
    private final String profileImagePath;
    private final List<PostDto> posts;

    public static FriendDto from(User user, List<Post> posts) {
        return FriendDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .posts(posts.stream()
                        .map(PostDto::from)
                        .collect(toList())
                )
                .build();
    }
}
