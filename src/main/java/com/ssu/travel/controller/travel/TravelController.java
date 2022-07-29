package com.ssu.travel.controller.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.dto.travel.TravelResponseDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.service.travel.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/travels")
public class TravelController {
    private final TravelService travelService;

    @GetMapping
    public List<TravelResponseDto> getAllTravels() {
        return travelService.getAllTravels().stream()
                .map(TravelResponseDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TravelResponseDto createTravel(@RequestBody @Valid TravelCreateRequestDto travelCreateRequestDto) {
        Travel travel = travelService.insertTravel(travelCreateRequestDto.toEntity());
        return new TravelResponseDto(travel);
    }

    @PostMapping("/{travelId}/users/{userId}")
    public TravelResponseDto addUserToTravel(@PathVariable Long travelId, @PathVariable Long userId) {
        travelService.addUserToTravel(travelId, userId);
        return new TravelResponseDto(travelService.getTravelById(travelId));
    }
}
