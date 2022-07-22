package com.ssu.travel.jpa.travel;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private TravelType travelType;
}
