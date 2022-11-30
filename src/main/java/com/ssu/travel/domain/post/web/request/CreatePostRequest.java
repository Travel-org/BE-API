package com.ssu.travel.domain.post.web.request;

import static lombok.AccessLevel.PROTECTED;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of")
public class CreatePostRequest {

    @NotNull(message = "schedule Id가 필요합니다.")
    private Long scheduleId;

    @NotNull(message = "제목이 필요합니다.")
    @NotBlank(message = "제목이 필요합니다.")
    private String title;

    @NotBlank
    private String text;

    private List<MultipartFile> photos;
}
