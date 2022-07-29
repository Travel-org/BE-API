package com.ssu.travel.service.place;


import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    @Transactional
    public Place insertPlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    @Transactional(readOnly = true)
    public Place findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("placeId 해당하는 주소가 없습니다."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Place> findPlacesByName(String placeName) {
        return placeRepository.findByName(placeName);
    }

    @Override
    @Transactional
    public void deleteAllPlaces() {
        placeRepository.deleteAll();
    }
}
