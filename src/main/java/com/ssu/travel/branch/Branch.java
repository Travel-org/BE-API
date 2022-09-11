package com.ssu.travel.branch;

import com.ssu.travel.schedule.Schedule;
import com.ssu.travel.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private Branch(User user, Schedule schedule) {
        this.user = user;
        this.schedule = schedule;
    }

    public static Branch of(User user, Schedule schedule) {
        return new Branch(user, schedule);
    }

}
