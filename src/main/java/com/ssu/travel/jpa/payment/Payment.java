package com.ssu.travel.jpa.payment;

import com.ssu.travel.jpa.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private Long amount;

    @Builder
    public Payment(@NonNull Reservation reservation, @NonNull Long amount) {
        this.reservation = reservation;
        this.amount = amount;
    }
}
