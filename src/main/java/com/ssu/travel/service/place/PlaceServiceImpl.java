package com.ssu.travel.service.place;


import com.ssu.travel.dto.place.PlaceResponseDto;
import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.jpa.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(PlaceResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlaceResponseDto insertPlace(Place place) {
        Place insertedPlace = placeRepository.save(place);
        return new PlaceResponseDto(insertedPlace);
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceResponseDto findPlaceById(Long placeId) {
        Place foundedPlace = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("placeId 해당하는 주소가 없습니다."));
        return new PlaceResponseDto(foundedPlace);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> findPlacesByName(String placeName) {
        return placeRepository.findByName(placeName)
                .stream()
                .map(PlaceResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllPlaces() {
        placeRepository.deleteAll();
    }
}
