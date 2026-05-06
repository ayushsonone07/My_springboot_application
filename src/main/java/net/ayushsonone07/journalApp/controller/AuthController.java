package net.ayushsonone07.journalApp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ayushsonone07.journalApp.dto.ApiResponse;
import net.ayushsonone07.journalApp.dto.AuthRequest;
import net.ayushsonone07.journalApp.dto.AuthResponse;
import net.ayushsonone07.journalApp.dto.RefreshTokenRequest;
import net.ayushsonone07.journalApp.service.AuthService;
import net.ayushsonone07.journalApp.service.impl.UserDetailsServiceImpl;
import net.ayushsonone07.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
