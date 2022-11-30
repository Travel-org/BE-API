package com.ssu.travel.domain.photo.web.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CRUDPhotoRequest {

    private int order;
    private String imageSource;
    private String extension;

    @Builder
    public CRUDPhotoRequest(int order, String imageSource, String extension) {
        this.order = order;
        this.imageSource = imageSource;
        this.extension = extension;
    }
}
