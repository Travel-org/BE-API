package com.ssu.travel.jpa.place;

import javax.persistence.*;

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
}
