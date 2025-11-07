package com.example.smartspend.service;
import com.example.smartspend.model.User;
import com.example.smartspend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final Map<String, Long> tokens = new ConcurrentHashMap<>();
    public AuthService(UserRepository userRepository){ this.userRepository = userRepository; }
    public User register(String name, String email, String password){
        var u = new User(name,email,password);
        return userRepository.save(u);
    }
    public String login(String email, String password){
        var opt = userRepository.findByEmail(email);
        if(opt.isEmpty()) return null;
        var u = opt.get();
        if(!u.getPassword().equals(password)) return null;
        var token = UUID.randomUUID().toString();
        tokens.put(token, u.getId());
        return token;
    }
    public Long userIdFromToken(String token){
        return tokens.get(token);
    }
    public void logout(String token){ tokens.remove(token); }
}
