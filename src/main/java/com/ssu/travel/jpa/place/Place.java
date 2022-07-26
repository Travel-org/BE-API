package com.ssu.travel.jpa.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private Double lat;

    private Double lng;

    private String placeName;

    private String phoneNumber;

    private String addressName;

    private String addressRoadName;

    private String placeUrl;

    @Builder
    public Place(@NonNull Double lat, @NonNull Double lng, @NonNull String placeName, String phoneNumber, @NonNull String addressName, @NonNull String addressRoadName, @NonNull String placeUrl) {
        this.lat = lat;
        this.lng = lng;
        this.placeName = placeName;
        this.phoneNumber = phoneNumber;
        this.addressName = addressName;
        this.addressRoadName = addressRoadName;
        this.placeUrl = placeUrl;
    }
}
