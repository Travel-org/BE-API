package com.ssu.travel.dto.place;

import com.ssu.travel.jpa.place.Place;
import lombok.Getter;

@Getter
public class PlaceResponseDto {
    private final Long placeId;
    private final Double lat;
    private final Double lng;
    private final String placeName;
    private final String phoneNumber;
    private final String addressName;
    private final String addressRoadName;
    private final String placeUrl;

    public PlaceResponseDto(Place entity) {
        this.placeId = entity.getId();
        this.lat = entity.getLat();
        this.lng = entity.getLng();
        this.placeName = entity.getPlaceName();
        this.phoneNumber = entity.getPhoneNumber();
        this.addressName = entity.getAddressName();
        this.addressRoadName = entity.getAddressRoadName();
        this.placeUrl = entity.getPlaceUrl();
    }
}
