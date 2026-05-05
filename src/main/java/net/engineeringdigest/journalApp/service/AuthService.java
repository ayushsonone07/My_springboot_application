package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.AuthRequest;
import net.engineeringdigest.journalApp.dto.AuthResponse;
import net.engineeringdigest.journalApp.dto.RefreshTokenRequest;
import net.engineeringdigest.journalApp.entity.RefreshToken;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    // Register new user
    public AuthResponse register(AuthRequest request) {

        // Check duplicate username
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already Exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))  // BCrypt encode
                .role(request.getRole() != null ? request.getRole() : "USER")
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(request.getEmail()); // ✅ email
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

        return new AuthResponse(token, refreshToken.getToken());
    }

    // Login existing user
    public AuthResponse login(AuthRequest request) {

        // This throws if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        String token = jwtService.generateToken(request.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

        return new AuthResponse(token,refreshToken.getToken());
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        String email = refreshToken.getUser().getEmail();
        String newAccessToken = jwtService.generateToken(email);

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();

    }

    public void logout(String email) {

        refreshTokenService.deleteByEmail(email);

    }
}

