package com.ssu.travel.jpa.reservation;

import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.travel.Travel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private String title;

    private String bookerName;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
