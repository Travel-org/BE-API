package com.ssu.travel.domain.branch.entity;

import com.ssu.travel.domain.schedule.entity.Schedule;
import com.ssu.travel.domain.user.entity.User;
import java.util.Objects;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "brach")
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

    public void updateUser(User user) {
        this.user = user;
    }

    // Todo: updateSchecdule
    public void updateSchedule(Schedule schedule) {
        if (Objects.nonNull(schedule)) {
            this.schedule.getBranches().remove(this);
        }
        this.schedule = schedule;
        schedule.getBranches().add(this);
    }
}
