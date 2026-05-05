package net.engineeringdigest.journalApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.RefreshToken;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.RefreshTokenRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;   // ✅ correct
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")           // ✅ Spring @Value reads from yml
    private String secret;

    @Value("${jwt.refresh-token}")    // ✅ Spring @Value reads from yml
    private long refreshTokenExpiry;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // Generate token
    public String generateToken(String username) {
        log.info("Generating token for: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Refresh Token
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

    //Verify Refresh Token
    public RefreshToken verifyRefreshToken(String token){

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh Token Not Found!"));

        if(refreshToken.isExpired()) {

            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired - Login again!");

        }

        return  refreshToken;
    }

    //Delete User
    public void deleteByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        refreshTokenRepository.deleteByUser(user);

    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info("Token username: {}", extractUsername(token));
        log.info("DB username: {}", userDetails.getUsername());
        log.info("Expired: {}", isTokenExpired(token));
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    // Check expiry
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}