package com.example.smartspend.controller;

import com.example.smartspend.model.User;
import com.example.smartspend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PublicLoginController {

    private final UserRepository userRepository;

    public PublicLoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // yahi endpoint frontend hit karega: POST /login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password) {

        // simple: sab users lao, email match karo
        List<User> users = userRepository.findAll();
        User matching = users.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst()
                .orElse(null);

        if (matching == null) {
            // user hi nahi mila
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // NOTE: yahan plain-text compare use kar rahe hain,
        // kyunki tumhare register code me password jaise ka taisa save hua hoga.
        if (!password.equals(matching.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // agar sab sahi: ek random token bana ke bhej do
        String token = UUID.randomUUID().toString();

        // Abhi ke design me hum token server pe store nahi kar rahe,
        // kyunki expenses APIs public "/public/..." ke through chal rahi hain.
        // Future me chaaho to token DB me save + validate kar sakte ho.

        return ResponseEntity.ok(token);
    }
}
