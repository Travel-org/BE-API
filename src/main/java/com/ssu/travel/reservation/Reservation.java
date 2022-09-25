package com.ssu.travel.reservation;

import com.ssu.travel.place.Place;
import com.ssu.travel.travel.Travel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private String bookerName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;


    private Reservation(String bookerName, LocalDateTime startTime, LocalDateTime endTime, Place place, Travel travel) {
        this.bookerName = bookerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.travel = travel;
    }

    public static Reservation of(String bookerName, LocalDateTime startTime, LocalDateTime endTime, Place place, Travel travel) {
        return new Reservation(bookerName, startTime, endTime, place, travel);
    }
}
