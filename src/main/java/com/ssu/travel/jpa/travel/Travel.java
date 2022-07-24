package com.ssu.travel.jpa.travel;

import com.ssu.travel.jpa.usertravel.UserTravel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @NotNull
    private Long managerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @OneToMany(mappedBy = "travel")
    private List<UserTravel> userTravels = new ArrayList<>();

    @Builder
    public Travel(Long managerId, String title, String memo,
                  LocalDate startDate,
                  LocalDate endDate,
                  TravelType travelType) {
        this.managerId = managerId;
        this.title = title;
        this.memo = memo;
        this.travelType = travelType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addUserTravel(UserTravel userTravel) {
        this.userTravels.add(userTravel);
    }
}
