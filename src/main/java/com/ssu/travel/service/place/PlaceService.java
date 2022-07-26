package com.ssu.travel.service.place;

import com.ssu.travel.dto.place.PlaceResponseDto;
import com.ssu.travel.jpa.place.Place;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlaceService {

    List<PlaceResponseDto> getAllPlaces();

    PlaceResponseDto insertPlace(Place place);

    PlaceResponseDto findPlaceById(Long placeId);

    List<PlaceResponseDto> findPlacesByName(String placeName);

    void deleteAllPlaces();
}
