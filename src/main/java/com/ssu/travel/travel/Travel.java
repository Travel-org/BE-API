package com.ssu.travel.travel;

import com.ssu.travel.material.Material;
//import com.ssu.travel.travel.travelDate.TravelDate;
import com.ssu.travel.travel.travelDate.TravelDate;
import com.ssu.travel.usertravel.UserTravel;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @Column(nullable = false)
    private Long managerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    @Setter
    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    private Integer budget;

    @OneToMany(mappedBy = "travel")
    private List<UserTravel> userTravels = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private final List<TravelDate> travelDates = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private final List<Material> materials = new ArrayList<>();


    public void addUserTravel(UserTravel userTravel) {
        userTravel.updateTravel(this);
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    public void removeMaterial(Material material) {
        this.materials.remove(material);
    }


    public void updateInfo(String title, String content, Integer budget) {
        this.title = title;
        this.content = content;
        this.budget = budget;
    }

    public void updateDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Builder
    private Travel(Long managerId, String title, String content, LocalDate startDate, LocalDate endDate, TravelType travelType, Integer budget, List<UserTravel> userTravels) {
        this.managerId = managerId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travelType = travelType;
        this.budget = budget;
        this.userTravels = userTravels;
    }

    public static Travel of(Long managerId, String title, String content, LocalDate startDate, LocalDate endDate, TravelType travelType, Integer budget) {
        return Travel.builder()
                .managerId(managerId)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .travelType(travelType)
                .budget(budget)
                .build();
    }

    public static Travel of(Long managerId, String title, String content, LocalDate startDate, LocalDate endDate, Integer budget) {
        return Travel.builder()
                .managerId(managerId)
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .budget(budget)
                .travelType(TravelType.PRIVATE)
                .build();
    }
}
