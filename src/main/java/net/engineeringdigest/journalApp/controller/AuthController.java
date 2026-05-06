package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.constants.ApiStatus;
import net.engineeringdigest.journalApp.dto.ApiResponse;
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
    public ApiResponse<AuthResponse> register(@RequestBody AuthRequest request) {

        return ApiResponse.created("Registration Successful",authService.register(request));

    }

    // Login
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest request) {

        return ApiResponse.success("Login Successful",authService.login(request));

    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody RefreshTokenRequest request){

        return  ApiResponse.success(authService.refreshToken(request));

    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody Map<String, String> request){

        authService.logout(request.get("email"));
        return ApiResponse.success("Logged out Successfully!");

    }

}
