package com.ssu.travel.domain.user.web.request;

import com.ssu.travel.domain.user.entity.Gender;
import com.ssu.travel.global.dto.UploadType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class ModifyUserRequest {

    @NotNull(message = "닉네임 값이 누락되었습니다.")
    private String nickname;

    private ProfileImage profileImage;

    @NotNull(message = "생년월일이 누락되었습니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @NotNull(message = "성별이 누락되었습니다.")
    private Gender gender;

    @Getter
    public static class ProfileImage {
        private UploadType uploadType;
        private String userProfileExtension;
        private String imageSource;
    }
}
