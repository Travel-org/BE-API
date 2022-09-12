package com.ssu.travel.cost;

import com.ssu.travel.travel.Travel;
import com.ssu.travel.usercost.UserCost;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cost_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_id")
    private Travel travel;

    private Long totalAmount;

    private Long payerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "cost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCost> userCosts = new ArrayList<>();

    public static Cost of(Travel travel, Long totalAmount, Long payerId, String title, String content) {
        Cost cost = new Cost();
        cost.travel = travel;
        cost.totalAmount = totalAmount;
        cost.payerId = payerId;
        cost.title = title;
        cost.content = content;
        return cost;
    }

    public void addUserCost(UserCost userCost) {
        this.userCosts.add(userCost);
    }

}
