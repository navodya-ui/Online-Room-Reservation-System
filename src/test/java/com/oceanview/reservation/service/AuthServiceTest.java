package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.UserRepository;
import com.oceanview.reservation.dto.LoginRequest;
import com.oceanview.reservation.exception.UnauthorizedException;
import com.oceanview.reservation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        tokenService = new TokenService();
        authService = new AuthService(userRepository, passwordEncoder, tokenService);
    }

    @Test
    void login_success_returnsToken() {
        User u = new User();
        u.setUsername("admin");
        u.setRole("ADMIN");
        u.setPasswordHash(passwordEncoder.encode("admin123"));

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(u));

        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("admin123");

        var res = authService.login(req);

        assertNotNull(res.getToken());
        assertEquals("admin", res.getUsername());
        assertEquals("ADMIN", res.getRole());
    }

    @Test
    void login_wrongPassword_throws401() {
        User u = new User();
        u.setUsername("admin");
        u.setRole("ADMIN");
        u.setPasswordHash(passwordEncoder.encode("admin123"));

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(u));

        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("wrong");

        assertThrows(UnauthorizedException.class, () -> authService.login(req));
    }
}
