package com.example.smartspend.controller;

import com.example.smartspend.service.AuthService;
import com.example.smartspend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

        // call service (assumes AuthService.register(name,email,password) exists)
        try {
            Object result = authService.register(name, email, password);
            // if service returns created user or token, forward it
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            // return 400 with message
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




