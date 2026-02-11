package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.UserRepository;
import com.oceanview.reservation.dto.LoginRequest;
import com.oceanview.reservation.dto.LoginResponse;
import com.oceanview.reservation.exception.UnauthorizedException;
import com.oceanview.reservation.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        boolean ok = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!ok) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String token = tokenService.createToken(user.getUsername());
        return new LoginResponse(token, user.getUsername(), user.getRole());
    }
}
