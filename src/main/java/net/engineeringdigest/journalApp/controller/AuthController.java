package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.AuthRequest;
import net.engineeringdigest.journalApp.dto.AuthResponse;
import net.engineeringdigest.journalApp.dto.RefreshTokenRequest;
import net.engineeringdigest.journalApp.service.AuthService;
import net.engineeringdigest.journalApp.service.impl.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl UserDetailsService;

    @Autowired
    private UserService userService;

//    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(authService.register(request)).ok(Map.of(
                "message", "User Registered Sucessfully",
                "Data", request
        ));

    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(authService.login(request));

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request){

        return  ResponseEntity.ok(authService.refreshToken(request));

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody Map<String, String> request){

        authService.logout(request.get("email"));
        return ResponseEntity.ok("Logged out Successfully!");

    }

}
