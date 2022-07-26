package com.ssu.travel.controller.place;

import com.ssu.travel.dto.place.PlaceResponseDto;
import com.ssu.travel.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public List<PlaceResponseDto> getAllPlaces() {
        return placeService.getAllPlaces();
    }
}
