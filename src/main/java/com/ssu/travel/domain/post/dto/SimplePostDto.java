package com.ssu.travel.domain.post.dto;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import com.ssu.travel.domain.photo.dto.PhotoDto;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.user.dto.SimpleUserDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
public class SimplePostDto {

    private final Long id;
    private final SimpleUserDto user;
    private final String title;
    private final String content;
    private final LocalDateTime createdTime;
    private final Long scheduleId;
    private final String placeName;
    private final String placeUrl;
    private final List<PhotoDto> photos;

    public static SimplePostDto from(Post post) {
        return SimplePostDto.builder()
                .id(post.getId())
                .user(SimpleUserDto.from(post.getUser()))
                .title(post.getTitle())
                .content(post.getContent())
                .createdTime(post.getCreatedTime())
                .scheduleId(post.getSchedule().getId())
                .placeName(post.getSchedule().getPlace().getPlaceName())
                .placeUrl(post.getSchedule().getPlace().getPlaceUrl())
                .photos(post.getPhotos().stream()
                        .map(PhotoDto::from)
                        .collect(toList()))
                .build();
    }
}
