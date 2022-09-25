package com.ssu.travel.place;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "kakao_map_id_idx", columnList = "kakaoMapId", unique = true)
})
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

    private Long kakaoMapId;


    private Place(Double lat, Double lng, String placeName, String phoneNumber, String addressName, String addressRoadName, String placeUrl, Long kakaoMapId) {
        this.lat = lat;
        this.lng = lng;
        this.placeName = placeName;
        this.phoneNumber = phoneNumber;
        this.addressName = addressName;
        this.addressRoadName = addressRoadName;
        this.placeUrl = placeUrl;
        this.kakaoMapId = kakaoMapId;
    }

    public static Place of(Double lat, Double lng, String placeName, String phoneNumber, String addressName, String addressRoadName, String placeUrl, Long kakaoMapId) {
        return new Place(lat, lng, placeName, phoneNumber, addressName, addressRoadName, placeUrl, kakaoMapId);
    }
}
