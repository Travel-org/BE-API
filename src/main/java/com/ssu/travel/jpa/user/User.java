package com.ssu.travel.jpa.user;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    @Column(length = 200, unique = true)
    @NotNull
    private String email;

    @Column(length = 30)
    @NotNull
    private String password;

    @Column(length = 20)
    @NotNull
    private String name;

    @Column(length = 20, unique = true)
    private String phoneNumber;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Sex sex;
}
