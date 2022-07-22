package com.ssu.travel.service.travel;

import com.ssu.travel.jpa.travel.TravelRepository;
import org.springframework.stereotype.Service;

@Service
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;

    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }
}
