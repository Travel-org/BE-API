package com.ssu.travel.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findAllByPlaceNameContaining(String placeName);

    Optional<Place> findByKakaoMapId(Long kakaoMapId);
}
