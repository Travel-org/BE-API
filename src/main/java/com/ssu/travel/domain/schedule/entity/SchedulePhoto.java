package com.ssu.travel.domain.schedule.entity;

import com.ssu.travel.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SchedulePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String photoPath;


    private SchedulePhoto(User user, Schedule schedule, String photoPath) {
        this.user = user;
        this.schedule = schedule;
        this.photoPath = photoPath;
    }

    public static SchedulePhoto of(User user, Schedule schedule, String photoPath) {
        return new SchedulePhoto(user, schedule, photoPath);
    }
}
