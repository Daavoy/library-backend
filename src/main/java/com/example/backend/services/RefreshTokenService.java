package com.example.backend.services;

import com.example.backend.models.RefreshToken;
import com.example.backend.models.UserInfo;
import com.example.backend.repositories.RefreshTokenRepository;
import com.example.backend.repositories.UserInfoRepository;
import exceptions.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.expiration.refresh}")
    private Long refresh;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserInfoRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserInfoRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Because RefreshToken is a OneToOne with UserInfo, a user can only
        // have one row at a time. Delete any existing token before inserting
        // a new one, otherwise the unique constraint on user_id is violated
        // on the user's second login.
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush(); // force the delete to commit before the insert below

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refresh));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        UserInfo user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}
