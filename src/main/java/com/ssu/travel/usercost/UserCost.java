package com.ssu.travel.usercost;

import com.ssu.travel.cost.Cost;
import com.ssu.travel.user.User;
import lombok.*;
import lombok.extern.java.Log;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cost_id")
    private Long id;

    private Long amount;

    private Boolean isRequested;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_id")
    private Cost cost;

    public void update(Long amount, Boolean isRequested) {
        this.amount = amount;
        this.isRequested = isRequested;
    }

    public void isRequested() {
        this.isRequested = true;
    }


    @Builder(access = AccessLevel.PRIVATE)
    private UserCost(Long amount, Boolean isRequested, User user, Cost cost) {
        this.amount = amount;
        this.isRequested = isRequested;
        this.user = user;
        this.cost = cost;
    }

    public static UserCost of(User user, Cost cost, Long amount) {
        return UserCost.builder()
                .user(user)
                .cost(cost)
                .amount(amount)
                .isRequested(false)
                .build();
    }

    public static UserCost of(User user, Cost cost, Long amount, Boolean isRequested) {
        return UserCost.builder()
                .user(user)
                .cost(cost)
                .amount(amount)
                .isRequested(isRequested)
                .build();
    }
}
