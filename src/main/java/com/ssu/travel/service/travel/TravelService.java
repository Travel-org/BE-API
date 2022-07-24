package com.ssu.travel.service.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.dto.travel.TravelResponseDto;
import com.ssu.travel.dto.user.UserSimpleInfoDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TravelService {
    TravelResponseDto createTravel(TravelCreateRequestDto requestDto);

    List<TravelResponseDto> getAllTravels();

    void addUserToTravel(Long travelId, Long userId);

    Travel getTravelById(Long travelId);

    List<User> getUsersOfTravel(Long travelId);

    List<UserSimpleInfoDto> getSimpleUsersOfTravel(Long travelId);
}
