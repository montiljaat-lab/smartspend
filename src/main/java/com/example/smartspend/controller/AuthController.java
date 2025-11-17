package com.example.smartspend.controller;

import com.example.smartspend.service.AuthService;
import com.example.smartspend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// 👇 YE NAYA IMPORT IMPORTANT HAI
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(
        origins = {
                "https://smartspend-flame.vercel.app", // Vercel frontend
                "http://localhost:8080"               // local testing
        },
        maxAge = 3600
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // register expects JSON { "name": "...", "email": "...", "password": "..." }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
        String name = body.getOrDefault("name","").toString();
        String email = body.getOrDefault("email","").toString();
        String password = body.getOrDefault("password","").toString();

        try {
            Object result = authService.register(name, email, password);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(Map.of("message", ex.getMessage()));
        }
    }

    // login expects JSON { "email": "...", "password": "..." }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,Object> body) {
        String email = body.getOrDefault("email","").toString();
        String password = body.getOrDefault("password","").toString();

        try {
            Object result = authService.login(email, password);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("message", ex.getMessage()));
        }
    }
}



