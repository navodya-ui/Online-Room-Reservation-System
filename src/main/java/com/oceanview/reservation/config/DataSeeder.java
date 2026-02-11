package com.oceanview.reservation.config;

import com.oceanview.reservation.dao.UserRepository;
import com.oceanview.reservation.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create admin if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }

        // Create staff if not exists
        if (!userRepository.existsByUsername("staff1")) {
            User staff = new User();
            staff.setUsername("staff1");
            staff.setPasswordHash(passwordEncoder.encode("staff123"));
            staff.setRole("STAFF");
            userRepository.save(staff);
        }
    }
}
