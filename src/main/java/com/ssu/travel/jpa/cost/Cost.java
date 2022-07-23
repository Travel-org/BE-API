package com.ssu.travel.jpa.cost;

import com.ssu.travel.jpa.travel.Travel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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

    @Column(columnDefinition = "TEXT")
    private String content;

    private String title;
}
