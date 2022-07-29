package com.ssu.travel.jpa.reservation;

import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.travel.Travel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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

    private String bookerName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public Reservation(@NonNull Place place, Travel travel, @NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull String bookerName) {
        this.place = place;
        this.travel = travel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookerName = bookerName;
    }
}
