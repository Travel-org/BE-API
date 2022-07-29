package com.ssu.travel.jpa.usercost;

import com.ssu.travel.jpa.cost.Cost;
import com.ssu.travel.jpa.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_cost_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_id")
    private Cost cost;

    private Long amount;

    @ColumnDefault("false")
    private Boolean isRequested;

    @Builder
    public UserCost(@NonNull Cost cost, @NonNull User user, @NonNull Long amount, @NonNull Boolean isRequested) {
        this.cost = cost;
        this.user = user;
        this.amount = amount;
        this.isRequested = isRequested;
    }
}
