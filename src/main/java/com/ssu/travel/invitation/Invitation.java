package com.ssu.travel.invitation;

import com.ssu.travel.travel.Travel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

//    @Type(type = "uuid-char")
    // TODO: DB 확인 해보기
    @Column(columnDefinition = "BINARY(16)")
    private UUID code;


    private Invitation(String email, Travel travel, UUID code) {
        this.email = email;
        this.travel = travel;
        this.code = code;
    }

    public static Invitation of(String email, Travel travel, UUID code) {
        return new Invitation(email, travel, code);
    }
}
