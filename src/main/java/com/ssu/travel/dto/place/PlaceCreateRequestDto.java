package com.ssu.travel.dto.place;

import com.ssu.travel.jpa.place.Place;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PlaceCreateRequestDto {
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private String placeName;
    private String phoneNumber;
    @NotNull
    private String addressName;
    @NotNull
    private String addressRoadName;
    @NotNull
    private String placeUrl;

    public Place toEntity() {
        return Place.builder()
                .lat(lat)
                .lng(lng)
                .placeName(placeName)
                .phoneNumber(phoneNumber)
                .addressName(addressName)
                .addressRoadName(addressRoadName)
                .placeUrl(this.placeUrl)
                .build();
    }
}
