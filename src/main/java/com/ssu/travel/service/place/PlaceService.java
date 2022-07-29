package com.ssu.travel.service.place;

import com.ssu.travel.jpa.place.Place;

import java.util.List;

public interface PlaceService {

    List<Place> getAllPlaces();

    Place insertPlace(Place place);

    Place findPlaceById(Long placeId);

    List<Place> findPlacesByName(String placeName);

    void deleteAllPlaces();
}
