package com.ssu.travel.controller.place;

import com.ssu.travel.dto.place.PlaceCreateRequestDto;
import com.ssu.travel.dto.place.PlaceResponseDto;
import com.ssu.travel.jpa.place.Place;
import com.ssu.travel.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public List<PlaceResponseDto> getAllPlaces() {
        return placeService.getAllPlaces().stream()
                .map(PlaceResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public PlaceResponseDto createPlace(@RequestBody @Valid PlaceCreateRequestDto placeCreateRequestDto) {
        Place place = placeService.insertPlace(placeCreateRequestDto.toEntity());
        return new PlaceResponseDto(place);
    }
}
