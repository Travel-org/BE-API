package com.ssu.travel.jpa.travel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

//    @NotNull
//    private Long managerId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @Builder
    public Travel(String title, String memo,
                  LocalDate startDate,
                  LocalDate endDate,
                  TravelType travelType) {
        this.title = title;
        this.memo = memo;
        this.travelType = travelType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
