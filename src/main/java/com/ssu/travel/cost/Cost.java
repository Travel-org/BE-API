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

    private Long totalAmount;

    private Long payerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_id")
    private Travel travel;

    @OneToMany(mappedBy = "cost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCost> userCosts = new ArrayList<>();

    public void addUserCost(UserCost userCost) {
        this.userCosts.add(userCost);
    }

    public void removeUserCost(UserCost userCost) {
        this.userCosts.remove(userCost);
    }

    public void update(Long totalAmount, Long payerId, String title, String content) {
        this.totalAmount = totalAmount;
        this.payerId = payerId;
        this.title = title;
        this.content = content;
    }


    private Cost(Travel travel, Long totalAmount, Long payerId, String title, String content) {
        this.travel = travel;
        this.totalAmount = totalAmount;
        this.payerId = payerId;
        this.title = title;
        this.content = content;
    }

    public static Cost of(Travel travel, Long totalAmount, Long payerId, String title, String content) {
        return new Cost(travel, totalAmount, payerId, title, content);
    }
}
