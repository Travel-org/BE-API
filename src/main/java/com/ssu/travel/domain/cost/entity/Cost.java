package com.ssu.travel.domain.cost.entity;

import com.ssu.travel.domain.travel.entity.Travel;
import com.ssu.travel.domain.usercost.entity.UserCost;
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

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long payerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="travel_id")
    private Travel travel;

    @OneToMany(mappedBy = "cost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCost> userCosts = new ArrayList<>();

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

    public void update(Long totalAmount, String title, String content, Long payerId) {
        this.totalAmount = totalAmount;
        this.title = title;
        this.content = content;
        this.payerId = payerId;
    }

    public void addUserCost(UserCost userCost) {
        this.userCosts.add(userCost);
    }

    public void removeUserCost(UserCost userCost) {
        this.userCosts.remove(userCost);
    }
}
