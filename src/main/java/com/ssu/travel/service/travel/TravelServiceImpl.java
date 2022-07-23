package com.ssu.travel.service.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;

    @Transactional
    public Long save(TravelCreateRequestDto travelCreateRequestDto) {
        Travel travelEntity = travelCreateRequestDto.toEntity();
        return travelRepository.save(travelEntity).getId();
    }
}
