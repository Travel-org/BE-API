package com.ssu.travel.jpa.branch;

import com.ssu.travel.jpa.schedule.Schedule;
import com.ssu.travel.jpa.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;


@Getter
@NoArgsConstructor
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

    @Builder
    public Branch(@NonNull User user, @NonNull Schedule schedule) {
        this.id = id;
        this.user = user;
        this.schedule = schedule;
    }
}
