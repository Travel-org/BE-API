package com.ssu.travel.usertravel;

import com.ssu.travel.travel.Travel;
import com.ssu.travel.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void updateTravel(Travel travel) {
        this.travel.getUserTravels().remove(this);
        this.travel = travel;
        travel.getUserTravels().add(this);
    }


    private UserTravel(User user, Travel travel) {
        this.user = user;
        this.travel = travel;
    }

    public static UserTravel of(User user, Travel travel) {
        return new UserTravel(user, travel);
    }
}
