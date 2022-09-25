package com.ssu.travel.payment;

import com.ssu.travel.reservation.Reservation;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    private Payment(Reservation reservation, Long amount) {
        this.reservation = reservation;
        this.amount = amount;
    }

    public static Payment of(Reservation reservation, Long amount) {
        return new Payment(reservation, amount);
    }
}
