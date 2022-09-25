package com.ssu.travel.user;


import com.ssu.travel.user.constant.Role;
import com.ssu.travel.user.constant.Sex;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "kakaoId", unique = true)
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private Long kakaoId;

    private String email;

    private String password;

    private String name;

    @Column(length = 20, unique = true)
    private String phoneNumber;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String profilePath;

    @Enumerated(EnumType.STRING)
    private Role role;


    public void update(String name, String phoneNumber, Sex sex, LocalDate birthDay) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birthday = birthDay;
    }

    public void changeProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @Builder
    private User(Long kakaoId, String email, String password, String name, String phoneNumber, LocalDate birthday, Sex sex, String profilePath, Role role) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.sex = sex;
        this.profilePath = profilePath;
        this.role = role;
    }

    public static User of(Long kakaoId, String email, String password, String name, String phoneNumber, LocalDate birthday, Sex sex, String profilePath, Role role) {
        return new User(kakaoId, email, password, name, phoneNumber, birthday, sex, profilePath, role);
    }
}
