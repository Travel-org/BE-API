package com.ssu.travel.domain.user.repository;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.oauth2.model.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, OAuth2Provider provider);
}
