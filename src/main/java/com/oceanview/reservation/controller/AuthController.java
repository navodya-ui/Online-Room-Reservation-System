package com.oceanview.reservation.controller;

import com.oceanview.reservation.dto.LoginRequest;
import com.oceanview.reservation.dto.LoginResponse;
import com.oceanview.reservation.service.AuthService;
import com.oceanview.reservation.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(value = "X-SESSION-TOKEN", required = false) String token
    ) {
        tokenService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
}
