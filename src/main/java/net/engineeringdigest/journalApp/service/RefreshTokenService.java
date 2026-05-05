package net.engineeringdigest.journalApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.RefreshToken;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.RefreshTokenRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-token}")
    private long refreshTokenExpiry;

    @Transactional
    public RefreshToken createRefreshToken(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found!"));

        refreshTokenRepository.deleteByUser(user); // needs transaction

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiry))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token){

         RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh Token Not Found!"));

         if(refreshToken.isExpired()) {

             refreshTokenRepository.delete(refreshToken);
             throw new RuntimeException("Refresh Token Expired - Login again!");

         }

         return  refreshToken;
    }

    public void deleteByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        refreshTokenRepository.deleteByUser(user);

    }
}
