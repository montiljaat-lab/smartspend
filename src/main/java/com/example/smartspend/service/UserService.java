package com.example.smartspend.service;

import com.example.smartspend.model.User;
import com.example.smartspend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.encoder = new BCryptPasswordEncoder();
    }

    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    public User register(User u) {
        // Hash the password before saving
        String raw = u.getPassword() == null ? "" : u.getPassword();
        String hashed = encoder.encode(raw);
        u.setPassword(hashed);
        return repo.save(u);
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public boolean validatePassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null) rawPassword = "";
        if (hashedPassword == null) return false;
        return encoder.matches(rawPassword, hashedPassword);
    }
}