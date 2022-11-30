package com.ssu.travel.domain.post.dto;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.ssu.travel.domain.comment.dto.CommentDto;
import com.ssu.travel.domain.photo.dto.PhotoDto;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
public class PostDto {

    private final Long id;
    private final SimpleUserDto user;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Long scheduleId;
    private final String placeName;
    private final String placeUrl;
    private final List<CommentDto> comments;
    private final List<PhotoDto> photos;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .user(SimpleUserDto.from(post.getUser()))
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedTime())
                .scheduleId(post.getSchedule().getId())
                .comments(post.getComments().stream()
                        .map(CommentDto::from)
                        .collect(toList()))
                .photos(post.getPhotos().stream()
                        .map(PhotoDto::from)
                        .collect(toList()))
                .build();
    }
}
