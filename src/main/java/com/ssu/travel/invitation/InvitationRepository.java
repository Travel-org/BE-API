package com.ssu.travel.invitation;

import com.ssu.travel.travel.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByCodeAndEmail(UUID code, String email);

    Optional<Invitation> findByEmailAndTravel(String email, Travel travel);
}
