package com.ssu.travel.domain.usercost.entity;

import com.ssu.travel.domain.cost.entity.Cost;
import com.ssu.travel.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_cost")
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

    public void update(Long amount, Boolean isRequested) {
        this.amount = amount;
        this.isRequested = isRequested;
    }

    public void isRequested() {
        this.isRequested = true;
    }
}
