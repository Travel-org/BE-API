package com.ssu.travel.domain.post.web.request;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of")
public class UpdatePostRequest {

    @NotBlank(message = "제목이 필요합니다.")
    private String title;

    private String text;

    private List<MultipartFile> addPhotos;

    private List<Long> removePhotoIds;
}
