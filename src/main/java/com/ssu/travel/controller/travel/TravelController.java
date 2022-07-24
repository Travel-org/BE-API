package com.ssu.travel.controller.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.dto.travel.TravelResponseDto;
import com.ssu.travel.service.travel.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/travels")
public class TravelController {
    private final TravelService travelService;

    @GetMapping
    public List<TravelResponseDto> getAllTravels() {
        return travelService.getAllTravels();
    }

    @PostMapping
    public TravelResponseDto createTravel(@RequestBody TravelCreateRequestDto requestDto) {
        return travelService.createTravel(requestDto);
    }

    @PostMapping("/{travelId}/users/{userId}")
    public TravelResponseDto addUserToTravel(@PathVariable Long travelId, @PathVariable Long userId) {
        travelService.addUserToTravel(travelId, userId);
        return new TravelResponseDto(travelService.getTravelById(travelId));
    }
}
