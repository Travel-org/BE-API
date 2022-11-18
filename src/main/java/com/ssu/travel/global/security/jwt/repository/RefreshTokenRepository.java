package com.ssu.travel.global.security.jwt.repository;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.jwt.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("select r from RefreshToken r join fetch r.user where r.token = :token")
    Optional<RefreshToken> findByToken(@Param("token") String token);

    @Query("select r from RefreshToken r join fetch r.user where r.user = :user and r.clientIp = :clientIp and r.userAgent = :userAgent")
    Optional<RefreshToken> findByDevice(@Param("user") User loginUser,
                                        @Param("clientIp") String clientIp,
                                        @Param("userAgent") String userAgent);

    void deleteByToken(String refreshToken);
}
