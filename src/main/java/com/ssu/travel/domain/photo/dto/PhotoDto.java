package com.ssu.travel.domain.photo.dto;

import static lombok.AccessLevel.PRIVATE;

import com.ssu.travel.domain.photo.entity.Photo;
import com.ssu.travel.domain.storage.service.FileStorageService;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = PRIVATE)
public class PhotoDto {  // extends ImageDto

    private final String imageSource;
    private final String extension;
    private final int order;

    public static PhotoDto from(Photo photo) {
        return PhotoDto.builder()
                .imageSource(FileStorageService.getFileSourceForPhotoDto(photo.getPath()))
                .extension(photo.getExtension())
                .order(photo.getOrder())
                .build();
    }
}
