package com.ssu.travel.jpa.usertravel;

import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserTravel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_travel_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @Builder
    public UserTravel(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
    }
}
