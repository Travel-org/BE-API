package com.ssu.travel.domain.user.entity;


import static com.ssu.travel.domain.user.entity.Role.ROLE_USER;

import com.ssu.travel.global.security.oauth2.model.OAuth2Provider;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", indexes = {
        @Index(columnList = "email", unique = true),
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    // 설정 안할 시 기본 이미지 등록
    private String profileImagePath;

    // true: 필수 정보 기입
    private boolean informationRequired;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    public static User of(String email, OAuth2Provider provider) {
        User user = new User();
        user.email = email;
        user.nickname = "익명의 사용자";
        user.profileImagePath = "/images/default_user_profile.jpeg";
        user.informationRequired = false;
        user.role = ROLE_USER;
        user.provider = provider;
        return user;
    }

    public static User convertUser(Long id, String email, Role role) {
        User converted = new User();
        converted.id = id;
        converted.email = email;
        converted.role = role;
        return converted;
    }

    public void update(String nickname, Gender gender, LocalDate birthday, String profileImagePath) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthday = birthday;
        if (profileImagePath != null) {
            this.profileImagePath = profileImagePath;
        }
        this.informationRequired = true;
    }

    public void changeProfilePath(String profilePath) {
        this.profileImagePath = profilePath;
    }

}
