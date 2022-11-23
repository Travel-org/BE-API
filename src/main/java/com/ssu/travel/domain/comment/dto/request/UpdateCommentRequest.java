package com.ssu.travel.domain.comment.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class UpdateCommentRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
