package com.ssu.travel.material;

import com.ssu.travel.travel.Travel;
import com.ssu.travel.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String material;

    private Boolean checked;

    public void update(User user, String material, Boolean checked) {
        this.user = user;
        this.material = material;
        this.checked = checked;
    }


    private Material(Travel travel, User user, String material, Boolean checked) {
        this.travel = travel;
        this.user = user;
        this.material = material;
        this.checked = checked;
    }

    public static Material of(Travel travel, User user, String material, Boolean checked) {
        return new Material(travel, user, material, checked);
    }
}
