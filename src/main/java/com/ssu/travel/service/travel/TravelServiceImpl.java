package com.ssu.travel.service.travel;

import com.ssu.travel.dto.travel.TravelCreateRequestDto;
import com.ssu.travel.dto.travel.TravelResponseDto;
import com.ssu.travel.dto.user.UserSimpleInfoDto;
import com.ssu.travel.jpa.travel.Travel;
import com.ssu.travel.jpa.travel.TravelRepository;
import com.ssu.travel.jpa.user.User;
import com.ssu.travel.jpa.user.UserRepository;
import com.ssu.travel.jpa.usertravel.UserTravel;
import com.ssu.travel.jpa.usertravel.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final UserTravelRepository userTravelRepository;

    @Override
    @Transactional
    public TravelResponseDto createTravel(TravelCreateRequestDto travelCreateRequestDto) {

        Optional<User> user = userRepository.findById(travelCreateRequestDto.getUserId());
        if (user.isEmpty()) {
            throw new RuntimeException("등록된 유저가 없다!!");
        }
        Travel travel = travelCreateRequestDto.toEntity();

        UserTravel userTravel = UserTravel.builder()
                .user(user.get())
                .travel(travel)
                .build();
        userTravelRepository.save(userTravel);

        travel.addUserTravel(userTravel);
        travelRepository.save(travel);
        return new TravelResponseDto(travel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TravelResponseDto> getAllTravels() {
        return travelRepository
                .findAll()
                .stream().map(TravelResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addUserToTravel(Long travelId, Long userId) {
        Travel travel = getTravelById(travelId);
        Optional<User> user = userRepository.findById(userId);

        UserTravel userTravel = UserTravel.builder()
                .user(user.get())
                .travel(travel)
                .build();

        userTravelRepository.save(userTravel);
        travel.addUserTravel(userTravel);
        travelRepository.save(travel);
    }

    @Override
    @Transactional(readOnly = true)
    public Travel getTravelById(Long travelId) {
        Optional<Travel> travel = travelRepository.findById(travelId);
        if (travel.isEmpty()) {
            throw new RuntimeException("등록된 여행 없다 !!");
        }
        return travel.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersOfTravel(Long travelId) {
        return getTravelById(travelId)
                .getUserTravels()
                .stream()
                .map(UserTravel::getUser)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSimpleInfoDto> getSimpleUsersOfTravel(Long travelId) {
        return getTravelById(travelId)
                .getUserTravels()
                .stream()
                .map(UserTravel::getUser)
                .map(u -> new UserSimpleInfoDto(u.getId(), u.getName()))
                .collect(Collectors.toList());
    }
}
