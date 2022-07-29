package com.ssu.travel.service.travel;

import com.ssu.travel.dto.user.SimpleUserInfoDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.user.User;

import java.util.List;

public interface TravelService {
    Travel insertTravel(Travel travel);

    List<Travel> getAllTravels();

    void addUserToTravel(Long travelId, Long userId);

    Travel getTravelById(Long travelId);

    List<User> getUsersOfTravel(Long travelId);

    List<SimpleUserInfoDto> getSimpleUsersOfTravel(Long travelId);

    void deleteAllTravels();
}
