package com.ssu.travel.global.security.jwt.service;

import com.ssu.travel.domain.user.entity.User;
import com.ssu.travel.global.security.jwt.entity.RefreshToken;
import com.ssu.travel.global.security.jwt.exception.NotFoundRefreshTokenException;
import com.ssu.travel.global.security.jwt.repository.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long saveRefreshToken(User currentUser, String token, String clientIp, String userAgent) {
        Optional<RefreshToken> findToken = refreshTokenRepository.findByDevice(currentUser, clientIp, userAgent);

        if (findToken.isPresent()) {
            RefreshToken findRefreshToken = findToken.get();
            findRefreshToken.changeToken(token);
            return findRefreshToken.getId();
        }
        RefreshToken newRefreshToken = RefreshToken.of(currentUser, token, clientIp, userAgent);
        refreshTokenRepository.save(newRefreshToken);
        return newRefreshToken.getId();
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundRefreshTokenException(HttpStatus.NOT_FOUND, "리프레쉬 토큰이 존재하지 않습니다."));
    }

    @Transactional
    public String renewRefreshToken(RefreshToken refreshToken, String token) {
        refreshToken.changeToken(token);
        return token;
    }

    @Transactional
    public void removeRefreshTokenByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
